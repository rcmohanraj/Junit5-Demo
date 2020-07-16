## Junit 5:

Junit is basically a testing framework for Java which requires Java 8.

**Junit5 Dependencies:**
1) Junit Platform which is a execution engine of test classes
2) Junit Jupiter which contains all the test program related modules
3) Junit Vintage which is useful for providing support with Junit 3 and 4.

**Junit Lift Cycle Hooks:**  
1) @BeforeAll
2) @AfterAll
3) @BeforeEach
4) @AfterEach

In our example we are writing test case for our MathUtil.java in the 
MathUtilTest.java under the package src/test/java

By default MathUtilTest instances are created method level **@TestInstance(TestInstance.Lifecycle.PER_METHOD)**. 
Which means for running each test method (annotated with @Test) the Junit engine 
will create a new instance of MathUtilTest and execute the test case.

If we want to avoid creating each method an instance, we need to set
the **@TestInstance(TestInstance.Lifecycle.PER_CLASS)** in the MathUtilTest class.

BeforeAll and AfterAll have two different behaviour based on the TestInstance.
1) If the TestInstance is default which is **TestInstance.Lifecycle.PER_METHOD**, which means
each method will create its own instance. BeforeAll method must be called before the 
creation instance of the class. Without creating an instance we can't call a method 
in java, so we should make this BeforeAll as static method, so that before 
creating instances per method in MathUtilTest, BeforeAll will be called because of static method.

@BeforeAll
static void beforeAllInit() {
    System.out.println("Init Before All Methods");
} 

2) If the TestInstance is set TestInstance.Lifecycle.PER_CLASS, 
Each class have one instance, in that case after creating the instance, the BeforeAll
method will be called, so there is no need to static method requires here.

@BeforeAll
void beforeAllInit() {
    System.out.println("Init Before All Methods");
} 

As name implies BeforeEach and AfterEach annotated methods will be running before 
and after for each test method execution. Usually BeforeEach annotated methods have
initialization of the source class like MathUtil and AfterEach annotated methods 
will have the cleanup/destroy object codes.

**@Order:**  
This annotation is used to decide which test method should run which order. But the 
unit test's shouldn't have any dependencies between them and it can run in any order. 
So basically we should avoid using this @Order annotation.

**@DisplayName:**  
This annotation is used to keep a name for each test method. So while we are exploring 
the test result, instead of method name we can see this description of a method, to get 
a clear idea on what method has run to success and failures. 

**@Disable:**  
This annotation is used to disable a test method which will be skipped by the engine.
This will be useful if we want to build the artifact and we are following TDD approach means
the build will get failed. Instead we can Disabled annotation to skil this test and the build
can be successful.

**@Nested:**  
This annotation is used to group multiple test methods for one single functionality. 
It can be applied only on class level. So we need to group multiple test methods under 
a class and assign @Nested for that newly created(DivideTest.java) class. 

**Conditional executions:**  
It have list of @Enable annotations, where the test methods will run only on specific
conditions.  
@EnabledOnOs(OS.LINUX)  
@EnabledOnJre(JRE.JAVA_8)  
@EnabledIfEnvironmentVariable()  
@EnabledIfSystemProperty()  

**Asserts:**  
We can do assert in each test method to arrive a test result.
assertEquals(expected, result, "message of failure");  
assertFalse(false);  
assertTrue(true);  
assertThrows(): for checking exception from the actual method.  
assertThrows(ClassType, labmda, "test case description");  
assertThrows(ArithmeticException.class, () -> mathUtil.divide(5, 0), "The divide method should divide two numbers");  

