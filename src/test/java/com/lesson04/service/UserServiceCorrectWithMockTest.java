package com.lesson04.service;

import com.javarush.lesson04.entity.User;
import com.javarush.lesson04.repository.Dao;
import com.javarush.lesson04.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceCorrectWithMockTest {

    private UserService userService;

    @Mock
    private Dao<User> daoMock;

    @BeforeEach
    void setUp() {
        userService = new UserService(daoMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        //given
        User user = new User(null, "Petrov", "asdfgh");
        User userInDb = new User(333L, "Petrov", "asdfgh");
        Optional<User> result = Optional.of(userInDb);
        when(daoMock.create(user)).thenReturn(result);
        when(daoMock.get(333L)).thenReturn(result);
        //when
        User newUser = userService.create(user);
        Long id = newUser.getId();
        User userInDbTest = userService.get(id);
        //then
        assertEquals(333, id);
        assertEquals("Petrov", userInDbTest.getName());
        verify(daoMock).create(Mockito.any(User.class));
    }

    @Test
    void getAll() {
        //given
        User ivanov = new User(null, "Ivanov", "qwerty");
        User sidorov = new User(null, "Sidorov", "123456");
        doReturn(Stream.of(ivanov, sidorov)).when(daoMock).getAll();
        //when
        List<User> all = userService.getAll();
        //then
        assertEquals(2, all.size());
        verify(daoMock).getAll();
    }

    @Test
    void get() {
        //given
        String name = "Petrov";
        String password = "asdfgh";
        User userInDb = new User(1L, name, password);
        Optional<User> result = Optional.of(userInDb);
        doReturn(result).when(daoMock).get(1L);
        //when
        User user = userService.get(1L);
        //then
        assertEquals(name, user.getName());
        verify(daoMock).get(anyLong());
    }

    @Test
    void update() {
        //given
        User user1 = new User(1L, "one", "one");
        User user2 = new User(1L, "Joe", "two");
        doReturn(Optional.of(user1), Optional.of(user2))
                .when(daoMock).get(anyLong());
        when(daoMock.update(Mockito.any(User.class)))
                .thenReturn(Optional.of(user2));
        //when
        User user = userService.get(1L);
        user.setName("Joe");
        userService.update(user);
        User userInDb = userService.get(1L);
        //then
        assertEquals("Joe", userInDb.getName());
        verify(daoMock).update(Mockito.any(User.class));
        verify(daoMock, times(2)).get(anyLong());
    }

    @Test
    void deleteById() {
        //given
        when(daoMock.deleteById(anyLong())).thenReturn(true, true);
        doReturn(
                Stream.of(new User(2L, "two", "two")),
                Stream.empty())
                .when(daoMock).getAll();
        //when
        userService.deleteById(1L);
        int firstCount = userService.getAll().size();
        userService.deleteById(2L);
        int secondCound = userService.getAll().size();
        //then
        assertEquals(1, firstCount);
        assertEquals(0, secondCound);
        verify(daoMock, times(2)).deleteById(anyLong());
        verify(daoMock, times(2)).getAll();
    }
}