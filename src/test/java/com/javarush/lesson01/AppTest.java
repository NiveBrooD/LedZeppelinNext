package com.javarush.lesson01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void getHello() {
        Assertions.assertEquals("Hello World!", App.getHello());
    }
}