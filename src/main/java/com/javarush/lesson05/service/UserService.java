package com.javarush.lesson05.service;

import com.javarush.lesson05.UserRunner;
import com.javarush.lesson05.entity.User;
import com.javarush.lesson05.repository.Dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Optional;

public class UserService {

    private static final Logger log = LogManager.getLogger(UserService.class);

    private final Dao<User> userDao;

    public UserService(Dao<User> userDao) {
        this.userDao = userDao;
    }

    public User create(User user) {
        log.info("create {}", user);
        Optional<User> userOptional = userDao.create(user);
        log.info("create end for user = {}", user);
        return userOptional.orElseThrow();
    }

    public List<User> getAll() {
        return userDao.getAll().toList();
    }

    public User get(Long id) {
        log.info("get by id = {}", id);
        Optional<User> user = userDao.get(id);
        return user.orElseThrow();
    }

    public User update(User user) {
        Optional<User> update = userDao.update(user);
        return update.orElseThrow();
    }

    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }


}
