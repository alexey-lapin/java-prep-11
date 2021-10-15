package com.github.alexeylapin.ocp.concurrency;

import com.github.alexeylapin.ocp.concurrency.support.SleepingRunnable;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.github.alexeylapin.ocp.concurrency.support.Support.println;
import static org.assertj.core.api.Assertions.assertThat;

public class CyclicBarrierTest {

    static class UncoordinatedPerformer {

        private void setUp() {
            new SleepingRunnable(randomDuration()).run();
            println("set up done");
        }

        private void execute() {
            new SleepingRunnable(randomDuration()).run();
            println("execute done");
        }

        private void tearDown() {
            new SleepingRunnable(randomDuration()).run();
            println("tear down done");
        }

        public void perform() {
            setUp();
            execute();
            tearDown();
        }

        private static Duration randomDuration() {
            return Duration.ofSeconds(ThreadLocalRandom.current().nextInt(0, 6));
        }

    }

    static class CoordinatedPerformer {

        private final CyclicBarrier b1;
        private final CyclicBarrier b2;

        public CoordinatedPerformer(CyclicBarrier b1, CyclicBarrier b2) {
            this.b1 = b1;
            this.b2 = b2;
        }

        private void setUp() {
            new SleepingRunnable(randomDuration()).run();
            println("set up done");
        }

        private void execute() {
            new SleepingRunnable(randomDuration()).run();
            println("execute done");
        }

        private void tearDown() {
            new SleepingRunnable(randomDuration()).run();
            println("tear down done");
        }

        public void perform() throws BrokenBarrierException, InterruptedException {
            setUp();
            b1.await();
            execute();
            b2.await();
            tearDown();
        }

        private static Duration randomDuration() {
            return Duration.ofSeconds(ThreadLocalRandom.current().nextInt(0, 6));
        }

    }

    @Test
    void name1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            var performer = new UncoordinatedPerformer();
            for (int i = 0; i < 4; i++) {
                executorService.submit(() -> performer.perform());
            }
        } finally {
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(20, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    @Test
    void name2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            CyclicBarrier b1 = new CyclicBarrier(4);
            CyclicBarrier b2 = new CyclicBarrier(4, () -> println("executed"));
            var performer = new CoordinatedPerformer(b1, b2);
            for (int i = 0; i < 4; i++) {
                executorService.submit(() -> {
                    performer.perform();
                    return 5;
                });
            }
        } finally {
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(20, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

}
