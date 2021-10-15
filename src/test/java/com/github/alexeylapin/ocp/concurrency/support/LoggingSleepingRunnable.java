package com.github.alexeylapin.ocp.concurrency.support;

import java.time.Duration;

public class LoggingSleepingRunnable extends SleepingRunnable {

    public LoggingSleepingRunnable(Duration duration) {
        super(duration);
    }

    @Override
    void onBefore() {
        Support.println("sleeping for " + millis);
    }

    @Override
    void onAfter() {
        Support.println("wake up");
    }

    @Override
    void onInterrupt(InterruptedException e) {
        Support.println("interrupted");
    }

}
