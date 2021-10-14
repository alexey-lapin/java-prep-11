package com.github.alexeylapin.ocp.concurrency;

import com.github.alexeylapin.ocp.concurrency.support.SleepingCallable;
import com.github.alexeylapin.ocp.concurrency.support.SleepingRunnable;
import com.github.alexeylapin.ocp.concurrency.support.Support;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class ThreadTest {

    @Test
    void name1() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        System.out.println(availableProcessors);
    }

    @Test
    void name2() {
        // @formatter:off
        Runnable r1 = () -> System.out.println("");
        Runnable r2 = () -> {int i = 1; i++;};
        Runnable r3 = () -> {return;};
        Runnable r4 = () -> {;};
        Runnable r5 = () -> {};
        // @formatter:on

        // Runnable r6 = () -> "";
        // Runnable r7 = () -> 5;
        // Runnable r8 = () -> { return new Object();};
    }

    // most common way to start a thread is to pass Runnable to Thread's constructor
    static class RunnableImplementor implements Runnable {
        @Override
        public void run() {
            System.out.println(RunnableImplementor.class.getSimpleName() + " is running");
        }
    }

    // the other way is to extend Thread class
    static class ThreadExtender extends Thread {
        @Override
        public void run() {
            System.out.println(ThreadExtender.class.getSimpleName() + " is running");
        }
    }

    // start() creates new thread and executes Runnable asynchronously
    // run() does not create new thread and blocks
    // the output order is undetermined
    @Test
    void name3() {
        System.out.println("begin");
        new Thread(new RunnableImplementor()).start();
        new ThreadExtender().start();
        System.out.println("end");
    }

    // wait()
    // notify()
    // join()

    static class T {

        public static int counter = 0;

    }

    @Test
    void name4() {
        new Thread(() -> {
            for (int i = 0; i < 500_000; i++) {
                T.counter++;
            }
        }).start();

        while (T.counter < 100) {
            System.out.println("not reached");
        }
        System.out.println("reached");
    }

    // ConcurrencyApi

    @Test
    void name5() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            Support.println("starting");

            executorService.execute(() -> Support.println("executing 1"));
            executorService.execute(() -> Support.println("executing 2"));
            executorService.execute(() -> Support.println("executing 3"));
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
        }
    }

    @Test
    void name6() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        assertThat(executorService.isShutdown()).isFalse();
        assertThat(executorService.isTerminated()).isFalse();

        try {
            Support.println("starting");

            executorService.execute(new SleepingRunnable(Duration.ofMillis(4000)));
            executorService.execute(new SleepingRunnable(Duration.ofMillis(2000)));
            executorService.execute(new SleepingRunnable(Duration.ofMillis(1000)));

            assertThat(executorService.isShutdown()).isFalse();
            assertThat(executorService.isTerminated()).isFalse();
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isFalse();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(Duration.ofMillis(3000))),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();

        new SleepingRunnable(Duration.ofMillis(10000)).run();
        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();
    }

    @Test
    void name7() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        assertThat(executorService.isShutdown()).isFalse();
        assertThat(executorService.isTerminated()).isFalse();

        try {
            Support.println("starting");

            executorService.execute(new SleepingRunnable(Duration.ofMillis(4000)));
            executorService.execute(new SleepingRunnable(Duration.ofMillis(2000)));
            executorService.execute(new SleepingRunnable(Duration.ofMillis(1000)));

            assertThat(executorService.isShutdown()).isFalse();
            assertThat(executorService.isTerminated()).isFalse();
        } finally {
            Support.println("finalizing");
            List<Runnable> runnables = executorService.shutdownNow();
            assertThat(runnables.size()).isEqualTo(2);
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isFalse();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(Duration.ofMillis(3000))),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();

        new SleepingRunnable(Duration.ofMillis(10000)).run();
        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();
    }

    @Test
    void name8() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Support.println("starting");
            List<Future<Integer>> futures = executorService.invokeAll(List.of(
                    new SleepingCallable<>(1, Duration.ofMillis(4000)),
                    new SleepingCallable<>(2, Duration.ofMillis(2000)),
                    new SleepingCallable<>(3, Duration.ofMillis(1000))));
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(Duration.ofMillis(3000))),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();
    }

    @Test
    void name9() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Support.println("starting");
            Integer result = executorService.invokeAny(List.of(
                    new SleepingCallable<>(1, Duration.ofMillis(4000)),
                    new SleepingCallable<>(2, Duration.ofMillis(2000)),
                    new SleepingCallable<>(3, Duration.ofMillis(1000))));
            assertThat(result).isEqualTo(1);
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(Duration.ofMillis(3000))),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();
    }

    @Test
    void name10() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Support.println("starting");
            Future<Integer> result = executorService.submit(new SleepingCallable<>(1, Duration.ofMillis(4000)));

            assertThat(result.isDone()).isFalse();
            assertThat(result.isCancelled()).isFalse();

            assertThat(result.cancel(true)).isTrue();

            assertThat(result.isDone()).isTrue();
            assertThat(result.isCancelled()).isTrue();

            Exception exception = catchThrowableOfType(() -> result.get(), CancellationException.class);
            assertThat(exception).isNotNull();
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
        }

    }

    @Test
    void name11() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Support.println("starting");
            Future<Integer> result = executorService.submit(new SleepingCallable<>(1, Duration.ofMillis(4000)));

            assertThat(result.isDone()).isFalse();
            assertThat(result.isCancelled()).isFalse();

            assertThat(result.get()).isEqualTo(1);

            assertThat(result.cancel(true)).isFalse();

            assertThat(result.isDone()).isTrue();
            assertThat(result.isCancelled()).isFalse();

            assertThat(result.get()).isEqualTo(1);
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
        }

    }

    // Scheduling


    @Test
    void name12() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        try {
            Support.println("starting");
            executorService.schedule(() -> Support.println("executing 1"), 3, TimeUnit.SECONDS);
            executorService.schedule(() -> Support.println("executing 2"), 3, TimeUnit.SECONDS);
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    @Test
    void name13() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        try {
            Support.println("starting");
            executorService.schedule(() -> Support.println("executing 1"), 3, TimeUnit.SECONDS);
            executorService.schedule(() -> 5, 3, TimeUnit.SECONDS);
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    @Test
    void name14() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        try {
            Support.println("starting");
            executorService.scheduleAtFixedRate(() -> Support.println("executing"), 1, 2, TimeUnit.SECONDS);
            new SleepingRunnable(Duration.ofMillis(8000)).run();
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    @Test
    void name15() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        try {
            Support.println("starting");
            executorService.scheduleWithFixedDelay(() -> Support.println("executing"), 1, 2, TimeUnit.SECONDS);
            new SleepingRunnable(Duration.ofMillis(8000)).run();
        } finally {
            Support.println("finalizing");
            executorService.shutdown();
            boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
            assertThat(terminated).isTrue();
        }
    }

    // Pools
    {
        Executors.newSingleThreadExecutor();
        Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
        Executors.newSingleThreadScheduledExecutor();
        Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
        Executors.newFixedThreadPool(5);
        Executors.newFixedThreadPool(5, Executors.defaultThreadFactory());
        Executors.newCachedThreadPool();
        Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        Executors.newScheduledThreadPool(5);
        Executors.newScheduledThreadPool(5, Executors.defaultThreadFactory());
    }


}
