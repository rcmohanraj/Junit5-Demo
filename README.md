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

To trigger test cases during maven lift cycle we need to add below plugin.  
```
<build>  
    <plugins>  
        <plugin>  
            <artifactId>maven-surefire-plugin</artifactId>  
            <version>3.0.0-M5</version>  
        </plugin>  
    </plugins>  
</build>
```  

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
a class and assign @Nested for that newly created DivideTest class. 

**Conditional executions:**  
It have list of @Enable annotations, where the test methods will run only on specific
conditions.  
@EnabledOnOs(OS.LINUX)  
@EnabledOnJre(JRE.JAVA_8)  
@EnabledIfEnvironmentVariable()  
@EnabledIfSystemProperty()  

**Assertions:**  
We can do assert in each test method to arrive a test result as success or failure.
Assert statements will compare against expected and actual value to find the result.
Also we can able to pass lambda for the 3rd param in assertEquals method. When the 
test case results in failure, the lambda will be called and message will be displayed 
in the test console. If we directly give the message, still we get the message in the 
case of failure. But the string manipulation will happen even if the test cases succeed
which is not required. (this is called Lazy Assert Message)   
```
assertEquals(expected, result, "message of failure"); //we can pass lambda instead of hard code message  
assertFalse(false);  
assertTrue(true);  
assertThrows();//for checking exception from the actual method.  
assertThrows(ClassType, labmda, "test case description");  
assertThrows(ArithmeticException.class, () -> mathUtil.divide(5, 0), "The divide method should divide two numbers");  
fail("Exception case); //to make the test case explicitly
```
**assertAll:**  
This method is used to take bunch of assert statements and run together and
display fail in case of any one assert failed. It will display success when
all the asserts are true. It will accept list of lambda functions as parameter.

**Assumptions:**  
Assumption is set of method where we can pass boolean value, based on that value
the junit engine will decide to run or skip the test.
assumeTrue(value);
For example if our method have connection to DB or server, in the case if the 
external system is not available our test case might run into failure. To 
avoid this type of test case failure because of external server we can use
assumptions. I'm asssuming that the DB connection is up, so that it can proceed for 
actual assertion test case to decide success or failure. If DB is down, this test
case will be skipped.
```
boolean isDBRunning = false;
assumeTrue(isDBRunning);//this should skip the test case as DB is down
``` 

**RepeatedTest:**  
This is used to repeat the test for n number of times. Instead of using @Test,
we need to simply use @RepeatedTest(3), it will run the test method 3 times.
We can also get the current repetition as param in the test method by using
RepetitionInfo. 
In RepetitionInfo class we have two methods, 
1.getCurrentRepetition()  
2.getCurrentRepetition()  

**Tag:**
 The @Tag is used to filter the cases by specific group or functionality. We
 can specify the required tags, while running test cases, either by configuring
 in IDE or maven. In maven we have to setup excludeGroups in the surefire-plugin 
 like below. Here we can add any number of tags to skip by separating using comma.
   
 ```
     <build>
         <plugins>
             <plugin>
                 <artifactId>maven-surefire-plugin</artifactId>
                 <version>3.0.0-M5</version>
                 <configuration>
                     <excludedGroups>
                         Circle, Math
                     </excludedGroups>
                 </configuration>
             </plugin>
         </plugins>
     </build>
```
 
 In IDE we can configure in the Run configuration of Junit like
 1) Edit Run Configuration 
 2) Choose Test Kind as Tags 
 3) Enter the tag name (Math)   
 if we click run it will run all the tags with name Math.
 
 **TestInfo and TestReporter:**
 Junit providing these two interfaces for getting to know the details about 
 test info and test reports. Junit doing dependency injection to add 
 implementation for these two interfaces. @BeforeAll method we can inject these
 two interfaces.
 
 TestInfo basically will give DisplayName, Tags and other meta info about each test methods
 TestReporter basically will give the Timestamp when the test started.
 ```
 @BeforeEach
 void init(TestInfo testInfo, TestReporter testReporter) {
     this.testInfo = testInfo;
     this.testReporter = testReporter;
     mathUtil = new MathUtil();
     testReporter.publishEntry("Running "+testInfo.getDisplayName() + " with Tags "+testInfo.getTags());
 }
 Output: 
 timestamp = 2020-07-16T11:07:07.096, value = Running Adding two valid number's with Tags [Math]
 ```
 
 We can do our own provider implementation and Junit can do dependency injection 
 on the provider whenever we request. (concept to be explored) 

------------------------------------------------------------------------------------------------------------------------ 