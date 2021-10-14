package com.github.alexeylapin.ocp.concurrency.support;

import java.time.Duration;
import java.util.concurrent.Callable;

public class SleepingCallable<T> implements Callable<T> {

    private final T result;
    private final long millis;

    public SleepingCallable(T result, Duration duration) {
        this.result = result;
        this.millis = duration.toMillis();
    }

    @Override
    public T call() throws Exception {
        Support.println("sleeping for " + millis);
        Thread.sleep(millis);
        Support.println("wake up");
        return result;
    }

}
