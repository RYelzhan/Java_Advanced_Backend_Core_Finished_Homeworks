# Java 8 and 9, 10, 11+ Module Practical Task
## Necessary Tools
- Java Development Kit 17+
- Apache Maven
- Git
## Environment
- **IDE:** IntelliJ , Eclipse
- **Build Tool:** Apache Maven
- **Version Control:** Git
- **Testing Framework:** JUnit 5

### Task List
1. Please use [jmp-parent](https://git.epam.com/epm-cdp/global-java-foundation-program/java-modules/-/tree/master/modules/101_Java_8_and_upper/jmp-parent?ref_type=heads) as base code for this task.
2. Implement `BankCard createBankCard(User user, BankCardType cardType)` method in `jmp-cloud-bank-impl` module. The method should return an object of `CreditBankCard` or `DebitBankCard` depending on the `BankCardType`.
3. Add implementation for `ServiceImpl` into `jmp-cloud-service-impl`.
4. Use `var` for the definition of local variables wherever it’s applicable.
5. Use lambdas and Java new features wherever it’s applicable.
## Evaluation Criteria
- **Code Quality :** Code should follow clean code principles and be well-structured.
- **Correctness :** The implementation should meet all task requirements and function correctly.
- **Use of Java Features :** Appropriate use of Java Stream API, lambdas, and other new features.
- **Testing :** Sufficient unit tests should be provided to cover key functionalities.
---

##### 4.	Create application module.
##### 5.	Add module-info.java which :
    - use interfaces
    - requires module with Bank implementation
    - requires module with Service implementation
    - requires jmp.dto;
##### 6.	Demonstrate functionality of created classes.
Tasks 7-9 weight: 40 points
##### 7.	Add a new default method for Service interface, which uses getAllUsers method. Use LocalDate.now(), ChronoUnit and mapToDouble.
    - double getAverageUsersAge();
##### 8.	Add a new static method for Service interface, which returns true, if the user is over 18 years old.
    - boolean isPayableUser(User);
##### 9.	Use Collectors.toUnmodifiableList() and method reference where it’s applicable.
Tasks 10-12 weight: 20 points
##### 10.	Create an Exception class to handle scenarios where a subscription is not found, specifically for use with the orElseThrow method on Optional within the getSubscriptionByBankCardNumber method.
    - Class Name:`SubscriptionNotFoundException`, extend the class from RuntimeException
    - Usage: Implement this exception to be thrown when orElseThrow is invoked on an Optional that does not contain a subscription.
##### 11.	Add a new method for Service interface and implement it. Demonstrate this method ino application module:
    - List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription>);
##### 12.	Reimplement createBankCard with method reference (ex: CreditBankCard::new).
## References
- [Java 8 new features](https://www.journaldev.com/2389/java-8-features-with-examples)
- [Java 9 new features](https://www.journaldev.com/13121/java-9-features-with-examples)
- [- modules](https://www.baeldung.com/java-9-modularity)
- [Java 10 new features](https://www.journaldev.com/20395/java-10-features)
- [- var](https://dzone.com/articles/var-work-in-progress)
- [Java 11 new features](https://www.journaldev.com/24601/java-11-features)
