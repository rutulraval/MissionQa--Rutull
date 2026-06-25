# QA Automation Assignment

We have added two tests: the first task is API-Test.feature and the second is UI-Test.feature.
- For the API-Test.feature, please visit https://reqres.in/. This should contain all the requirements.
- UI-Test.feature please visit https://www.saucedemo.com/


Please DO use Page objects, make sure the code is reusable and feel free to improve the current code.

**Note: We have intentionally added some bugs for you to debug.** 

Please contact the Mission Team if you have any questions.


Good luck!

## Test Automation Framework

- This is a Maven based framework
- `pom.xml` should have everything you need to create and run the tests. Please add further dependencies if you require it.

The following folders contain the core test orchestration classes:

- `src/test/java/hooks/Hook.java` - before/after hooks used to initialize and close browser sessions for `@ui` scenarios.
- `src/test/java/runner/RunnerTest.java` - contains `CucumberOptions` to run the BDD scenarios.

The following folder contains browser setup logic:

- `src/main/java/driver/BrowserSetup.java` - browser initialization based on `Browser` property from `TestData.properties`.

 
## Steps to execute this project

- Pre-requisites
    - JAVA SDK 11 or higher
    - Maven CLI
    
- Steps
    - Clone the project to local
    - Got o command line or any IDE that supports JAVA & Maven dependencies
    - We may need to import the Maven dependencies (Scope got set to Compile for Newly added dependencies in pom.xml)
    - Execute the command: `mvn clean test`
    - Alternatively, run `runner.RunnerTest` from IDE after downloading dependencies
    - Result will be captured in `test-output` folder

