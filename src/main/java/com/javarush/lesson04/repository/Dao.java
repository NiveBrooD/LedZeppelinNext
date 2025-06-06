package com.javarush.lesson04.repository;

import com.javarush.lesson04.entity.User;

import java.util.Optional;
import java.util.stream.Stream;

public interface Dao<T> {
    Stream<User> getAll();

    Optional<T> get(Long id);

    Optional<T> create(T entity);

    Optional<T> update(T entity);

    boolean deleteById(Long id);
}
