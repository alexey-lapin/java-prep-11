package com.github.alexeylapin.ocp.concurrency;

import com.github.alexeylapin.ocp.concurrency.support.SleepingRunnable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.alexeylapin.ocp.concurrency.support.Support.println;
import static com.github.alexeylapin.ocp.concurrency.support.Support.randomDuration;

public class LivenessTest {

    interface Resource {
    }

    static class Spoon implements Resource {
    }

    static class Soup implements Resource {

    }

    static class Person {

        private final Resource r1;
        private final Resource r2;
        private final CountDownLatch countDownLatch;

        public Person(Resource r1, Resource r2, CountDownLatch countDownLatch) {
            this.r1 = r1;
            this.r2 = r2;
            this.countDownLatch = countDownLatch;
        }

        public void perform() {
            synchronized (r1) {
                println("acquired " + r1);
                new SleepingRunnable(randomDuration(0, 2)).run();
                synchronized (r2) {
                    println("acquired " + r2);
                    countDownLatch.countDown();
                }
            }
        }

    }

    @Test
    void name() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        Spoon spoon = new Spoon();
        Soup soup = new Soup();

        Person person1 = new Person(spoon, soup, latch);
        Person person2 = new Person(soup, spoon, latch);

        new Thread(() -> person1.perform()).start();
        new Thread(() -> person2.perform()).start();

        latch.await();
    }

    class Res {

        private final Lock lock = new ReentrantLock();
        private final CountDownLatch latch;
        private final String name;

        public Res(CountDownLatch latch, String name) {
            this.latch = latch;
            this.name = name;
        }

        public void doLocked(Res res, Runnable action) {
            try {
                while (true) {
                    if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                        println("acquired lock on" + name);
                        if (res.lock.tryLock()) {
                            println("acquired lock on" + res.name);
                            action.run();
                            latch.countDown();
                            break;
                        } else {
                            println("releasing lock on " + name);
                            new SleepingRunnable(randomDuration(0, 2)).run();
                            lock.unlock();
                        }
                    }
                }
                res.lock.unlock();
                lock.unlock();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                println("interrupted");
            }
        }

    }

    @Test
    void name2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        Res resource1 = new Res(latch, "resource1");
        Res resource2 = new Res(latch, "resource2");
        new Thread(() -> resource1.doLocked(resource2, () -> println("12")), "T1").start();
        new Thread(() -> resource2.doLocked(resource1, () -> println("21")), "T2").start();

        latch.await();
    }

}
