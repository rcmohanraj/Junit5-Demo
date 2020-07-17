package it.mohanrc;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Running Math Utils Test")
class MathUtilTest {

    MathUtil mathUtil;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    static void beforeAllInit() {
        System.out.println("Init Before All Methods");
    }

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtil = new MathUtil();
        testReporter.publishEntry("Running "+testInfo.getDisplayName() + " with Tags "+testInfo.getTags());
    }

    @AfterEach
    void clean() {
        System.out.println("clean up done...");
    }

    @Test
    @DisplayName("Adding two valid number's")
    @Tag("Math")
    void testAdd() {
        int expected = 2;
        int result = mathUtil.add(1, 1);
        assertEquals(expected, result, "The add method should add two numbers");
    }

    @Test
    @DisplayName("Multiply two valid number's")
    @Tag("Math")
    void testMultiply() {
        assertAll(
                () -> assertEquals(1, mathUtil.multiply(1, 1)),
                () -> assertEquals(0, mathUtil.multiply(5, 0)),
                () -> assertEquals(-10, mathUtil.multiply(-5, 2))
        );
    }

    @Nested
    @Tag("Math")
    class DivideTest {
        @Test
        @DisplayName("Divide two valid number's")
        void testDivide() {
            int expected = 1;
            int result = new MathUtil().divide(5, 5);
            assertEquals(expected, result, "The divide method should divide two numbers");
        }

        @Test
        @DisplayName("Divide two valid number's with external dependency")
        void testDivideAssume() {
            boolean isDBRunning = false;
            assumeTrue(isDBRunning);//this should skip the test case as DB is down
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


    @RepeatedTest(3)
    @DisplayName("Compute Circle Area")
    @Tag("Circle")
    void computeCircleArea() {
//        if(repetitionInfo.getCurrentRepetition() == 1) {
//            assertEquals(314.1592653589793, mathUtil.computeCircleArea(1), "Compute Area of the Circle in the 1st repetition");
//        } else {
//            assertEquals(314.1592653589793, mathUtil.computeCircleArea(10), "Compute Area of the Circle");
//        }
        assertEquals(314.1592653589793, mathUtil.computeCircleArea(10), "Compute Area of the Circle in the 1st case");
    }

    @Test
    @DisplayName("Compute Circle Area 2nd Case")
    @Tag("Circle")
    void computeCircleAreaSecondCase() {
        assertEquals(3.141592653589793, mathUtil.computeCircleArea(1), "Compute Area of the Circle in the 2nd case");
    }

    @Test
    @DisplayName("TDD inprogress.")
    @Disabled
    void testMultiplyExceptionCase() {
        MathUtil mathUtil = new MathUtil();
        fail("Implementation not yet started");
    }

}