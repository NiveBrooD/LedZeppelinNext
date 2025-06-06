package com.javarush.lesson04;

import com.javarush.lesson04.entity.User;
import com.javarush.lesson04.repository.UserMemoryRepository;
import com.javarush.lesson04.service.UserService;

public class UserRunner {
    static Log log = new Log();

    public static void main(String[] args) {
        log.info("app started");
        try {
            UserMemoryRepository userMemoryRepository = new UserMemoryRepository();
            UserService userService = new UserService(userMemoryRepository);
            User user = new User(null, "Ivan", "qwerty");
            User userFromDb = userService.create(user);
            log.info("create user {}", user);
            userFromDb.setName("Oleg");
            userService.update(userFromDb);
            log.info("updated user {}", user);
            Long id = userFromDb.getId();
            userService.deleteById(id);
            log.info("deleteById user by id {}", id);
            userService.deleteById(id);
            log.info("second deleteById user by id {}", id);
        } catch (RuntimeException e) {
            log.error("catch error {}", e);
        }
    }

    static class Log {
        void trace(String format, Object... args) {
            info(format, args);
        }

        void debug(String format, Object... args) {
            info(format, args);
        }

        void info(String format, Object... args) {
            for (Object arg : args) {
                String output = format.replace("{}", arg.toString());
                System.out.println(output);
            }
        }

        void warn(String format, Object... args) {
            info(format, args);
        }

        void error(String format, Object... args) {
            for (Object arg : args) {
                String output = format.replace("{}", arg.toString());
                System.err.println(output);
            }
        }
    }
}
