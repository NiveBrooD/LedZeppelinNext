package com.javarush.lesson05.repository;

import com.javarush.lesson05.entity.User;

import java.util.*;
import java.util.stream.Stream;

public class UserMemoryRepository implements Dao<User> {


    private final Map<Long, User> map = new HashMap<>();

    @Override
    public Optional<User> create(User user) {
        long id = Id.nextId();
        user.setId(id);
        map.put(id, user);
        return Optional.of(user);
    }

    @Override
    public Stream<User> getAll() {
        return map.values().stream();
    }

    @Override
    public Optional<User> get(Long id) {
        User user = map.get(id);
        if (Objects.nonNull(user)) {
            return Optional.of(user);
        }
        throw new NoSuchElementException("not found user with id=" + id);
    }

    @Override
    public Optional<User> update(User entity) {
        User storageUser = get(entity.getId()).orElseThrow();
        storageUser.setName(entity.getName());
        storageUser.setPassword(entity.getPassword());
        return Optional.of(storageUser);
    }

    @Override
    public boolean deleteById(Long id) {
        User removed = map.remove(id);
        if (Objects.nonNull(removed)) {
            return true;
        }
        throw new NoSuchElementException("not found user with id=" + id);
    }

    @Override
    public String toString() {
        return "Repo";
    }
}
