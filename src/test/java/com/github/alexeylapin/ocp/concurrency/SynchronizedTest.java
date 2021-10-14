package com.github.alexeylapin.ocp.concurrency;

import com.github.alexeylapin.ocp.concurrency.support.Support;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class SynchronizedTest {

    static class Unsynchronized {

        private int counter = 0;

        public int getCounter() {
            return counter;
        }

        public void incrementAndReport() {
            Support.println(++counter + " ");
        }

    }

    static class Synchronized {

        private int counter = 0;

        public synchronized void incrementAndReport() {
            Support.println(++counter + " ");
        }

    }

    @Test
    void name1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        Unsynchronized unynchronized = new Unsynchronized();

        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> unynchronized.incrementAndReport());
            }
        } finally {
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    @Test
    void name2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        Synchronized aSynchronized = new Synchronized();

        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> aSynchronized.incrementAndReport());
            }
        } finally {
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

}
