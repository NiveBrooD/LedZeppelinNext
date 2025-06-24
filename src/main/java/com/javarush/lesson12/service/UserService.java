package com.javarush.lesson12.service;

import com.javarush.lesson12.model.User;

public class UserService {

    public User getDefaultUser(){
        return new User("Javarush", "qwerty");
    }

    public User create(String login, String password) {
        return new User(login, password);
    }
}
