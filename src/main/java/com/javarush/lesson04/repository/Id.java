package com.javarush.lesson04.repository;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Id {

    private static final AtomicLong counter = new AtomicLong();

    public static long newId() {
        return counter.incrementAndGet();
    }

}
