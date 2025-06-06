package com.lesson04.service;

import com.javarush.lesson04.entity.User;
import com.javarush.lesson04.repository.Dao;
import com.javarush.lesson04.repository.UserMemoryRepository;
import com.javarush.lesson04.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserServiceIncorrectTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        Dao<User> dao = new UserMemoryRepository();
        userService = new UserService(dao);
        User ivanov= new User(null, "Ivanov", "qwerty");
        User sidorov= new User(null, "Sidorov", "123456");
        userService.create(ivanov);
        userService.create(sidorov);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        User user = new User(null, "Petrov", "asdfgh");
        User newUser = userService.create(user);
        Long id = newUser.getId();
        User userInDb = userService.get(id);
        Assertions.assertEquals(3, id);
        Assertions.assertEquals("Petrov", userInDb.getName());
    }

    @Test
    void getAll() {
        List<User> all = userService.getAll();
        Assertions.assertEquals(2, all.size());
    }

    @Test
    void get() {
        User user = userService.get(1L);
        Assertions.assertEquals("Ivanov", user.getName());
    }

    @Test
    void update() {
        User user = userService.get(1L);
        user.setName("Joe");
        userService.update(user);
        User userInDb= userService.get(1L);
        Assertions.assertEquals("Joe", userInDb.getName());
    }

    @Test
    void deleteById() {
        userService.deleteById(1L);
        List<User> all = userService.getAll();
        Assertions.assertEquals(1, all.size());
        userService.deleteById(2L);
        all = userService.getAll();
        Assertions.assertEquals(0, all.size());
    }
}