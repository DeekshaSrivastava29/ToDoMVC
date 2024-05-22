# ToDoList

## Description
UI Test Suite to test 'ToDoList application' implemented in Scala using Selenium WebDriver.Dependencies such as ScalaTest, Selenium, WebDriverManager, and Typesafe Config have been added to the project.

Implemented automated tests using ScalaTest for both positive and negative scenarios. These tests cover various functionalities of the ToDoList application, including adding, editing, deleting, and filtering tasks.

Implemented Hooks for setting up and tearing down test environments, ensuring the WebDriver is initialized before each test and properly terminated afterward.

# Prerequisites
Java Development Kit (JDK): Ensure you have JDK installed on your system

Scala Build Tool (SBT): SBT is required to compile and run the Scala project

Web Browser: Since the project involves web automation using Selenium, ensure you have a compatible web browser installed on your system. Chrome is recommended.
# Installation
1. Clone this repo to the directory of your choice   
```git clone git@github.com:DeekshaSrivastava29/ToDoMVC.git```
2. Navigate to the project directory:
   ```cd ToDoList```
3. Build the project using sbt:
   ```sbt clean compile```

# How to run the tests
There are two specs 'PositiveSpec' and 'NegativeSpec' which can be run using the shell script 
```./run_tests.sh```
The `run_tests.sh` script defaults to the `local` environment with the locally installed `chrome` driver

# Reporting
Test report is generated in folder - ```/target/test-reports```

# Screenshots
Screenshots will be added after the end of each scenario in the folder - ```/ToDoList/test-reports```

# Additional Information
**Configuration**: If needed, you can modify the test configurations such as browser settings or test data in the src/test/resources/application.conf file.

**Contact:** For any questions or suggestions, feel free to reach out to Deeksha Srivastava at dikss92@gmail.com

**Contributions**: Contributions to the project are welcome! If you have any improvements or new features to suggest, please open a pull request on the repository.

Happy testing!