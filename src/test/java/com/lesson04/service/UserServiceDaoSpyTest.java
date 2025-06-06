package com.lesson04.service;

import com.javarush.lesson04.entity.User;
import com.javarush.lesson04.repository.Id;
import com.javarush.lesson04.repository.UserMemoryRepository;
import com.javarush.lesson04.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceDaoSpyTest {

    private UserService userService;

    @Spy
    private UserMemoryRepository daoSpy;
    private MockedStatic<Id> idMockedStatic;

    @BeforeEach
    void setUp() {
        idMockedStatic = mockStatic(Id.class);
        final AtomicLong id = new AtomicLong();
        idMockedStatic.when(Id::newId).thenAnswer(env -> id.incrementAndGet());
        userService = new UserService(daoSpy);
        User ivanov = new User(null, "Ivanov", "qwerty");
        User sidorov = new User(null, "Sidorov", "123456");
        userService.create(ivanov);
        userService.create(sidorov);
    }

    @AfterEach
    void tearDown() {
        idMockedStatic.close();
    }

    @Test
    void create() {
        //given
        User petrov = new User(null, "Petrov", "asdfgh");
        //when
        User newUser = userService.create(petrov);
        Long id = newUser.getId();
        //then
        assertEquals(3, id);
        assertEquals("Petrov", newUser.getName());
        verify(daoSpy).create(petrov);
    }

    @Test
    void getAll() {
        //when
        List<User> all = userService.getAll();
        //then
        assertEquals(2, all.size());
        verify(daoSpy).getAll();
    }

    @Test
    void get() {
        //when
        User user = userService.get(1L);
        //then
        assertEquals("Ivanov", user.getName());
        verify(daoSpy).get(anyLong());
    }

    @Test
    void update() {
        //given
        String password = "wqeqw";
        User petrov = new User(1L, "Petrov", password);
        //when
        User resultUser = userService.update(petrov);
        //then
        assertEquals(1L, resultUser.getId());
        assertEquals(password, resultUser.getPassword());
        verify(daoSpy).update(any(User.class));
    }

    @Test
    void deleteById() {
        //when
        boolean deleteById = userService.deleteById(1L);
        //then
        assertTrue(deleteById);
        verify(daoSpy).deleteById(anyLong());
    }
}