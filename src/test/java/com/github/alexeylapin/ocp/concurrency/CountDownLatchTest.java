package com.github.alexeylapin.ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    @Test
    void name() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(2);

        new Thread(new Worker(startSignal, doneSignal)).start();
        new Thread(new Worker(startSignal, doneSignal)).start();

        startSignal.countDown();
        doneSignal.await();
    }
}

class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    public void run() {
        try {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException ex) {
        }
    }

    void doWork() {
        System.out.println("doWork()");
    }
}