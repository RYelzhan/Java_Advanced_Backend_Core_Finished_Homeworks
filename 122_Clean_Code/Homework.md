# Code Quality Module Practical Task

## Overall Evaluation Criteria:
- Each task carries a maximum of 25 points for completeness.
- Adherence to SOLID principles and clean code standards.
- Implementation and justification of design patterns used.
- Robustness and scalability of the solution.
- Quality and comprehensiveness of documentation and tests.

## Necessary Tools:
- Java Development Kit 17+

---

## Task 1: Optimize and Re-architect a Library Management System's Book Lending Feature

### Objective:
Refactor and optimize the book lending process in a library management system by applying SOLID principles, design patterns, and ensuring clean code.

### Environment:
- Java 17+

### Steps:
1. **Re-design the Library Management System:**
    - Apply the Single Responsibility Principle (SRP) to ensure each class has a clear purpose.
    - Use the Open/Closed Principle (OCP) to allow the system to be extendable without modifying existing code when adding new features like book reservations.
    - Improve modularity and readability by applying appropriate design patterns (e.g., Factory, Strategy, Observer).

2. **Optimize Data Handling:**
    - Implement a better data management system (using collections, or integrating a simple database) for robust storage and retrieval.
    - Ensure thread safety if you foresee concurrent modifications to the book details.

3. **Refactor Existing Functions:**
    - Renovate old code by splitting large methods, using meaningful naming, and removing any duplicate code.
    - Enhance error handling to manage exceptions gracefully and provide clear error messages.

4. **Implement Advanced Features:**
    - Add the ability for users to reserve books if they are currently loaned out.
    - Implement a system to track and handle overdue books, issuing fines accordingly.

5. **Documentation and Testing:**
    - Provide comprehensive documentation for methods and classes.
    - Write thorough unit tests and integration tests for all functionalities, focusing on edge cases and failure scenarios.

### Provided Code:
```java
public class LibrarySystem {
    private HashMap<String, String> bookRegistry = new HashMap<>();

    public void checkOutBook(String bookId, String userId) {
        if (bookRegistry.containsKey(bookId)) {
            System.out.println("Book is currently unavailable.");
        } else {
            bookRegistry.put(bookId, userId);
            System.out.println("Book checked out to " + userId);
        }
    }

    public void returnBook(String bookId) {
        if (bookRegistry.containsKey(bookId)) {
            bookRegistry.remove(bookId);
            System.out.println("Book returned.");
        } else {
            System.out.println("This book was not checked out.");
        }
    }

    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        library.checkOutBook("BK001", "USR001");
        library.returnBook("BK001");
    }
}
```

---

## Task 2: Refactor the Employee Class
### Objective:
Refactor the Employee class to eliminate switch expressions in the `calculatePay` and `calculateBonus` methods, following the Open-Closed Principle.

### Environment:
- Java 17+


### Steps:
1. **Design an Employee Hierarchy:**
    - Create an abstract base class or interface named `Employee` with common properties like name.
    - Define abstract methods `calculatePay()` and `calculateBonus()`.

2. **Implement Subclasses for Different Employee Types:**
    - Create classes such as `CommissionedEmployee`, `HourlyEmployee`, and `SalariedEmployee`.
    - Implement the `calculatePay` and `calculateBonus` methods specifically for each type.

3. **Refactor Existing Methods:**
    - Replace switch statements in original Employee class methods with polymorphism.

4. **Update Client Code as Necessary:**
    - Adjust client code to use the new class structure.

5. **Ensure Thorough Testing:**
    - Write unit tests for each class to ensure that pay and bonus calculations are correct.

### Provided Code:
```java
public class Employee {
    private String name;
    private String type;

    public Money calculatePay(Employee e) throws InvalidEmployeeType {
        switch (e.type) {
            case COMMISSIONED:
                return calculateCommissionedPay(e);
            case HOURLY:
                return calculateHourlyPay(e);
            case SALARIED:
                return calculateSalariedPay(e);
            default:
                throw new InvalidEmployeeType(e.type);
        }
    }

    public Money calculateBonus(Employee e) throws InvalidEmployeeType {
        switch (e.type) {
            case COMMISSIONED:
                return calculateCommissionedBonus(e);
            case HOURLY:
                return calculateHourlyBonus(e);
            case SALARIED:
                return calculateSalariedBonus(e);
            default:
                throw new InvalidEmployeeType(e.type);
        }
    }
}
```

---

## Task 3: Critical Evaluation for Liskov Substitution Principle

### Objective:
Evaluate a Java codebase for a payment processing system to ensure it adheres to the Liskov Substitution Principle (LSP) and refactor as necessary.

### Environment:
- Java 17+

### Steps:
1. **In-depth Code Review:**
    - Identify LSP violations in the payment processing system.

2. **Strategic Refactoring:**
    - Refactor subclass implementations without altering the existing API.
    - Consider design patterns (e.g., Strategy or Template Method).

3. **Integration and Testing:**
    - Develop unit tests to verify all payment methods perform correctly in their contexts.

4. **Document Findings and Changes:**
    - Document LSP violations and the rationale behind changes.

5. **Evaluate and Optimize:**
    - Enhance performance without compromising security or functionality.

### Provided Code:
```java
abstract class PaymentMethod {
    abstract boolean validatePaymentDetails();
    abstract void processPayment(double amount);
}

class CreditCardPayment extends PaymentMethod {
    boolean validatePaymentDetails() {
        // Validate credit card details
    }

    void processPayment(double amount) {
        // Process credit card payment
    }
}

class PayPalPayment extends PaymentMethod {
    boolean validatePaymentDetails() {
        // Validate PayPal account
    }

    void processPayment(double amount) {
        // Log in to PayPal and process payment
        // Throws UnsupportedOperationException if the PayPal account is not linked to a bank account
    }
}

public class PaymentProcessor {
    public void makePayment(PaymentMethod payment, double amount) {
        if (payment.validatePaymentDetails()) {
            payment.processPayment(amount);
        }
    }
}
```

---

## Task 4: SonarQube Analysis

### Objective:
Create a Java web application project with common code structures and patterns, including intentional issues, to run a SonarQube analysis.

### Environment:
- Java 17+
- Spring Boot

### Steps:
1. **Project Framework and Structure:**
    - Use Spring Boot for the web application.
    - Include REST endpoints.
    - Integrate with H2 (in-memory database).

2. **Introduce Intentional Issues:**
    - Bugs: Logical errors and runtime exceptions.
    - Vulnerabilities: Security flaws like SQL injection.
    - Code Smells: Complex code, long methods, large classes, duplicated code.

3. **Implementation:**
```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@RestController
class SampleController {
    @GetMapping("/unsafe")
    public String unsafeMethod(String input) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
            Statement statement = connection.createStatement();
            // Intentional SQL Injection flaw
            statement.executeQuery("SELECT * FROM user_data WHERE user_name = '" + input + "'");
            return "Data Retrieved";
        } catch (SQLException e) {
            return "Error in SQL Handling";
        }
    }

    @GetMapping("/logic-error")
    public int faultyLogic() {
        int a = 10;
        int b = 0;
        // Intentional Division by Zero error
        return a / b;
    }
}
```

### Dependencies:
#### Maven:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
#### Gradle:
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
runtimeOnly 'com.h2database:h2'
```

### Evaluation Criteria:
- **Setup Completeness:** Ensure all dependencies are correctly configured.
- **Intentional Flaws:** Code should have identifiable issues (Bugs, Vulnerabilities, Code Smells).
- **Documentation:** Explain intentional flaws and reasons for their inclusion.
