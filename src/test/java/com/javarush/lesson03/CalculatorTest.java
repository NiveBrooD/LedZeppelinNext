package com.javarush.lesson03;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class CalculatorTest {

    private static Calculator calculator;

    @BeforeAll
    static void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("when add numbers then return sum")
    void whenAddNumbers_thenReturnSum() {
        int expected = 4;
        double actual = calculator.add(1, 3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void whenSubtractNumbers_thenReturnSubtraction() {
        int expected = 4;
        double actual = calculator.subtract(7, 3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void multiply() {
        double expected = 4.0;
        double actual = calculator.multiply(2, 2);
        Assertions.assertAll(List.of(
                () -> Assertions.assertEquals(expected, actual, 0.0001),
                () -> Assertions.assertEquals(expected, actual, 0.001),
                () -> Assertions.assertEquals(expected, actual, 0.01),
                () -> Assertions.assertEquals(expected, actual, 0.1)
        ));
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 2, 2",
            "40, 2, 20",
            "2, 1, 2",
            "10,3,3.33333"
    })
    void divide(int fistOperand, int secondOperand, double expected) {
        double actual = calculator.divide(fistOperand, secondOperand);
        Assertions.assertEquals(expected, actual, 0.001, "Check result");
    }

    @AfterEach
    void tearDown() {
    }
}