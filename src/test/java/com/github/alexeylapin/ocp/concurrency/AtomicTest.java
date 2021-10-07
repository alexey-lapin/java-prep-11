package com.github.alexeylapin.ocp.concurrency;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicTest {

    {
        new AtomicBoolean();
        new AtomicInteger();
        new AtomicLong();

        new AtomicReference<>();
    }

    {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.get();
        atomicInteger.set(1);
        atomicInteger.getAndSet(1);
        atomicInteger.incrementAndGet();
        atomicInteger.getAndDecrement();
        atomicInteger.decrementAndGet();
        atomicInteger.getAndDecrement();
    }

}
