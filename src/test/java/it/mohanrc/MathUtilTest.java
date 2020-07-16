package it.mohanrc;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.parallel.Execution;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MathUtilTest {

    MathUtil mathUtil;

    @BeforeAll
    static void beforeAllInit() {
        System.out.println("Init Before All Methods");
    }

    @BeforeEach
    void init() {
        mathUtil = new MathUtil();
    }

    @AfterEach
    void clean() {
        System.out.println("clean up done...");
    }

    @Test
    @DisplayName("Adding two valid number's")
    void add() {
        int expected = 2;
        int result = mathUtil.add(1, 1);
        assertEquals(expected, result, "The add method should add two numbers");
    }

    @Nested
    static class DivideTest {
        @Test
        @DisplayName("Divide two valid number's")
        void testDivide() {
            int expected = 1;
            int result = new MathUtil().divide(5, 5);
            assertEquals(expected, result, "The divide method should divide two numbers");
        }

        @Test
        @DisplayName("Divide by zero exception case")
        void testDivideExceptionCase() {
            assertThrows(ArithmeticException.class, () -> new MathUtil().divide(5, 0), "The divide method should divide two numbers");
        }
    }


    @Test
    @DisplayName("Compute Circle Area")
    void computeCircleArea() {
        MathUtil mathUtil = new MathUtil();
        assertEquals(314.1592653589793, mathUtil.computeCircleArea(10), "Compute Area of the Circle");
    }

    @Test
    @DisplayName("TDD inprogress.")
    @Disabled
    void testMultiply() {
        MathUtil mathUtil = new MathUtil();
        fail("Implementation not yet started");
    }

}