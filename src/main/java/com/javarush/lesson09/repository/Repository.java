package com.javarush.lesson09.repository;

import com.javarush.lesson09.model.Message;

import java.util.Map;

public interface Repository<T> {

    T findById(Long id);

    T saveOrUpdate(T entity);

    boolean delete(T entity);

    Map<Long, Message> getAll();

}
