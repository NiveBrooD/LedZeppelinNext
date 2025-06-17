package com.javarush.lesson05.repository;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Id {

    private static final AtomicLong counter = new AtomicLong();

    public static long nextId() {
        return counter.incrementAndGet();
    }

}
