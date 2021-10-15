package com.github.alexeylapin.ocp.concurrency.support;

import java.time.Duration;

public class SleepingRunnable implements Runnable {

    protected final long millis;

    public SleepingRunnable(Duration duration) {
        this.millis = duration.toMillis();
    }

    @Override
    public void run() {
        try {
            onBefore();
            Thread.sleep(millis);
            onAfter();
        } catch (InterruptedException e) {
            onInterrupt(e);
            Thread.currentThread().interrupt();
        }
    }

    void onBefore() {
    }

    void onAfter() {
    }

    void onInterrupt(InterruptedException e) {
    }

}
