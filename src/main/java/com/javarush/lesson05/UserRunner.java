package com.javarush.lesson05;

import com.javarush.lesson05.entity.User;
import com.javarush.lesson05.repository.UserMemoryRepository;
import com.javarush.lesson05.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserRunner {

    private static final Logger log = LogManager.getLogger(UserRunner.class);

    public static void main(String[] args) {

        log.info("app started {}", "first");
        log.warn("examples");
        log.error("error");
        log.warn("warn");
        log.info("info {} {} {}", "Hello", "World", '!');
        log.debug("debug");
        log.trace("trace");

        try {
            UserMemoryRepository userMemoryRepository = new UserMemoryRepository();
            UserService userService = new UserService(userMemoryRepository);
            User user = new User(null, "Ivan", "qwerty");
            User userFromDb = userService.create(user);
            log.trace("create user {}", user);
            userFromDb.setName("Oleg");
            userService.update(userFromDb);
            log.info("updated user {}", user);
            Long id = userFromDb.getId();
            userService.deleteById(id);
            log.info("deleteById user by id {}", id);
            userService.deleteById(id);
            log.info("second deleteById user by id {}", id);
        } catch (RuntimeException e) {
            log.error("catch error {}", "!!!!", e);
        }
    }
}
