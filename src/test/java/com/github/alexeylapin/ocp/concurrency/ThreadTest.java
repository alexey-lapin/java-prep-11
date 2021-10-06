package com.github.alexeylapin.ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

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
            println("starting");

            executorService.execute(() -> println("executing 1"));
            executorService.execute(() -> println("executing 2"));
            executorService.execute(() -> println("executing 3"));
        } finally {
            println("finalizing");
            executorService.shutdown();
        }
    }

    static class SleepingRunnable implements Runnable {

        private final int millis;

        public SleepingRunnable(int millis) {
            this.millis = millis;
        }

        @Override
        public void run() {
            try {
                println("sleeping for " + millis);
                Thread.sleep(millis);
                println("wake up");
            } catch (InterruptedException e) {
                println("interrupted");
                Thread.currentThread().interrupt();
            }
        }

    }

    static class SleepingCallable<T> implements Callable<T> {

        private final T result;
        private final int millis;

        public SleepingCallable(T result, int millis) {
            this.result = result;
            this.millis = millis;
        }

        @Override
        public T call() throws Exception {
            println("sleeping for " + millis);
            Thread.sleep(millis);
            println("wake up");
            return result;
        }

    }

    @Test
    void name6() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        assertThat(executorService.isShutdown()).isFalse();
        assertThat(executorService.isTerminated()).isFalse();

        try {
            println("starting");

            executorService.execute(new SleepingRunnable(4000));
            executorService.execute(new SleepingRunnable(2000));
            executorService.execute(new SleepingRunnable(1000));

            assertThat(executorService.isShutdown()).isFalse();
            assertThat(executorService.isTerminated()).isFalse();
        } finally {
            println("finalizing");
            executorService.shutdown();
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isFalse();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(3000)),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();

        new SleepingRunnable(10000).run();
        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();
    }

    @Test
    void name7() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        assertThat(executorService.isShutdown()).isFalse();
        assertThat(executorService.isTerminated()).isFalse();

        try {
            println("starting");

            executorService.execute(new SleepingRunnable(4000));
            executorService.execute(new SleepingRunnable(2000));
            executorService.execute(new SleepingRunnable(1000));

            assertThat(executorService.isShutdown()).isFalse();
            assertThat(executorService.isTerminated()).isFalse();
        } finally {
            println("finalizing");
            List<Runnable> runnables = executorService.shutdownNow();
            assertThat(runnables.size()).isEqualTo(2);
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isFalse();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(3000)),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();

        new SleepingRunnable(10000).run();
        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();
    }

    @Test
    void name8() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            println("starting");
            List<Future<Integer>> futures = executorService.invokeAll(List.of(
                    new SleepingCallable<>(1, 4000),
                    new SleepingCallable<>(2, 2000),
                    new SleepingCallable<>(3, 1000)));
        } finally {
            println("finalizing");
            executorService.shutdown();
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(3000)),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();
    }

    @Test
    void name9() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            println("starting");
            Integer result = executorService.invokeAny(List.of(
                    new SleepingCallable<>(1, 4000),
                    new SleepingCallable<>(2, 2000),
                    new SleepingCallable<>(3, 1000)));
            assertThat(result).isEqualTo(1);
        } finally {
            println("finalizing");
            executorService.shutdown();
        }

        assertThat(executorService.isShutdown()).isTrue();
        assertThat(executorService.isTerminated()).isTrue();

        Exception exception = catchThrowableOfType(
                () -> executorService.execute(new SleepingRunnable(3000)),
                RejectedExecutionException.class);
        assertThat(exception).isNotNull();
    }

    @Test
    void name10() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            println("starting");
            Future<Integer> result = executorService.submit(new SleepingCallable<>(1, 4000));

            assertThat(result.isDone()).isFalse();
            assertThat(result.isCancelled()).isFalse();

            result.cancel(true);

            assertThat(result.isDone()).isTrue();
            assertThat(result.isCancelled()).isTrue();

            Exception exception = catchThrowableOfType(() -> result.get(), CancellationException.class);
            assertThat(exception).isNotNull();
        } finally {
            println("finalizing");
            executorService.shutdown();
        }

    }

    public static void println(String message) {
        System.out.println(Thread.currentThread() + " - " + message);
    }

}
