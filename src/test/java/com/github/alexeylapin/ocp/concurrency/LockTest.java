package com.github.alexeylapin.ocp.concurrency;

import com.github.alexeylapin.ocp.concurrency.support.Support;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class LockTest {

//    {
//        Lock lock = new ReentrantLock();
//        lock.lock();
//        lock.unlock();
//        boolean result1 = lock.tryLock();
//        boolean result2 = lock.tryLock(10, TimeUnit.SECONDS);
//    }

    static class Locked1 {

        private final Lock lock = new ReentrantLock();
        private int counter;

        public void incrementAndReport() {
            try {
                lock.lock();
                Support.println(++counter + "");
            } finally {
                lock.unlock();
            }
        }

    }

    static class Locked2 {

        private final Lock lock = new ReentrantLock();
        private int counter;

        public void incrementAndReport() {
            if (lock.tryLock()) {
                try {
                    Support.println(++counter + "");
                } finally {
                    lock.unlock();
                }
            } else {
                Support.println("failed to acquire lock");
            }
        }

    }

    static class Locked3 {

        private final Lock lock = new ReentrantLock();
        private int counter;

        public void incrementAndReport() throws InterruptedException {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    Support.println(++counter + "");
                } finally {
                    lock.unlock();
                }
            } else {
                Support.println("failed to acquire lock");
            }
        }

    }

    @Test
    void name1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Locked1 locked = new Locked1();
        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> locked.incrementAndReport());
            }
        } finally {
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    @Test
    void name2() {
        Lock lock = new ReentrantLock();

        Exception exception = catchThrowableOfType(() -> lock.unlock(), IllegalMonitorStateException.class);
        assertThat(exception).isNotNull();
    }

    @Test
    void name3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Locked2 locked = new Locked2();
        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> locked.incrementAndReport());
            }
        } finally {
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    @Test
    void name4() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Locked3 locked = new Locked3();
        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {
                    locked.incrementAndReport();
                    return 5;
                });
            }
        } finally {
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    // unlock() exact same times as lock()

}
