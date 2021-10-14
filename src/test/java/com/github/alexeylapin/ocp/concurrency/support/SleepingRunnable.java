package com.github.alexeylapin.ocp.concurrency.support;

import java.time.Duration;

public class SleepingRunnable implements Runnable {

    private final long millis;

    public SleepingRunnable(Duration duration) {
        this.millis = duration.toMillis();
    }

    @Override
    public void run() {
        try {
            Support.println("sleeping for " + millis);
            Thread.sleep(millis);
            Support.println("wake up");
        } catch (InterruptedException e) {
            Support.println("interrupted");
            Thread.currentThread().interrupt();
        }
    }

}
