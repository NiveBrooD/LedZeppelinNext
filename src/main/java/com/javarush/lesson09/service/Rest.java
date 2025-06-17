package com.javarush.lesson09.service;

import java.util.Map;

public interface Rest<T> {

    Map<Long,T> getAll();

    T get(Long id);

    boolean post(T entity);

    boolean put(T entity);

    boolean delete(T entity);
}
