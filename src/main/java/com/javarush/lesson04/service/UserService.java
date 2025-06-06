package com.javarush.lesson04.service;

import com.javarush.lesson04.entity.User;
import com.javarush.lesson04.repository.Dao;

import java.util.List;
import java.util.Optional;

public class UserService {


    private final Dao<User> userDao;

    public UserService(Dao<User> userDao) {
        this.userDao = userDao;
    }

    public User create(User user) {
        Optional<User> userOptional = userDao.create(user);
        return userOptional.orElseThrow();
    }

    public List<User> getAll() {
        return userDao.getAll().toList();
    }

    public User get(Long id) {
        return userDao.get(id).orElseThrow();
    }

    public User update(User user) {
        Optional<User> update = userDao.update(user);
        return update.orElseThrow();
    }

    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }


}
