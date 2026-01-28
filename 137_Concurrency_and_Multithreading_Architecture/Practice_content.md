# Concurrency and Multi-threading Architecture

# 1.  Understanding Threads and ExecutorService (25 points)
## Task Overview
You are tasked with developing a Java console application to process multiple text files concurrently, first using basic threads and then refactoring the implementation to use `ExecutorService`. This step-by-step progression will help you understand the advantages of using `ExecutorService` over traditional threads for managing concurrent tasks more efficiently.

## Part 1: Using Plain Java Threads (15 points)
### Objectives
1. Learn how to create and manage threads manually.
2. Understand thread lifecycle and basic synchronization mechanisms.
### Project Setup
- Create a standard Java project with appropriate directory structure.
### Implementation
1. **Create a `FileProcessor` class**:
    - Implements `Runnable`.
    - Constructor takes a `String filePath`.
    - `run()` method reads the file line by line, processes each line (e.g., converts it to uppercase), and prints it.
2. **Main Application**:
    - Identify all text files to be processed (placed in a specific directory).
    - For each file, create a new `Thread` instance wrapping a `FileProcessor`.
    - Start each thread.
    - Use `join()` on each thread to ensure that the main thread waits for all threads to complete before exiting.
### Points of Learning
- Handling multiple threads and understanding the challenges such as race conditions.
- Using synchronization tools like `join()` to control thread execution order.

## Part 2: Refactoring to Use ExecutorService (10 points)
### Objectives
1. Replace manual thread management with `ExecutorService`.
2. Explore advanced threading features provided by the Java Concurrency API.
### Refactoring Steps
1. **Modify the Main Application**:
    - Replace the manual array of `Thread` objects with an `ExecutorService`.
    - Use `Executors.newFixedThreadPool()` to limit the number of threads.
    - Submit instances of `FileProcessor` to the executor.
    - After all tasks are submitted, shut down the executor using `shutdown()`.
    - Call `awaitTermination()` to block the main thread until all tasks complete.
2. **Enhance `FileProcessor`**:
    - Optionally manage exceptions within each task more cleanly.
    - Refactor long-running or complex processing code to be more interruptible.
### Points of Learning
- Benefits of using `ExecutorService` such as thread pooling, lifecycle management, and task scheduling.
- Handling graceful shutdown and ensuring all tasks complete using `ExecutorService` methods.
### Deliverables for Both Parts
- Source code files.
- A README file providing instructions on how to build and run the application, as well as brief notes on the implementation strategy for both parts.
  This task sequence is designed to provide a hands-on learning experience with Java multi-threading both with and without using `ExecutorService`. It aims to illustrate the evolution from managing threads manually to adopting a modern, robust, and scalable approach with `ExecutorService`.


# 2. Task: Implementing a Java Thread Interruption Handling Example (25 points)
## Description
Create a small Java console application that demonstrates the use and handling of interrupted exceptions when working with threads. This exercise will help you understand how to properly use thread interruptions to manage stopping threads gracefully.
## Objectives
1. Understand how the interruption mechanism in Java threads works.
2. Implement correct handling of `InterruptedException`.
## Requirements
- Java 8 or above.
## Task Details
### 1. Setup Project Structure
- Create a Java project and set up a suitable package structure, e.g., `com.example.threadinterruption`.
### 2. Creating the `InterruptibleTask` Class
- This class will implement the `Runnable` interface.
- The `run` method should contain a loop that performs a time-consuming operation, such as counting up to a large number or simulating a time-consuming computational algorithm.
- Inside the loop, add a check to see if the thread has been interrupted (`Thread.currentThread().isInterrupted()`). If interrupted, break the loop, clean up necessary resources if any, and exit.
- Log messages to the console to demonstrate the task startup, interruption notice, and task shutdown.
### 3. Main Application Class
- In the `main` method, start a thread using the `InterruptibleTask`.
- Sleep the main thread for a short duration sufficient to allow the task to start executing (e.g., 1000 milliseconds).
- Interrupt the thread running the `InterruptibleTask`.
- Ensure the application waits for the thread to finish using the `Thread.join()` method.
### 4. Testing Different Scenarios
- Test by interrupting the thread at different stages of its execution to observe the behavior.
- Optionally, modify the `InterruptibleTask` to include waiting or sleeping states using `Thread.sleep()`, and check how the task handles `InterruptedException`.
## Expected Output
- The console should clearly show the thread starting, handling interruption, and then terminating gracefully.
## Steps to Run
- Provide precise commands to compile and run the application, detailing any required environment setup.
## Deliverables
- Java source code files.
- A README file explaining the implementation, how to run the test cases, and any assumptions made.
  This task is designed to help you understand effective thread management, focusing on correctly handling interruptions to ensure that threads do not leave the system in an inconsistent state. This is fundamental for developing robust multi-threaded applications in Java.



# 3. Task: Refactor a Java Service to Be Stateless for Multithreading Environment (25 points)
## Description
Convert a stateful Java service into a stateless service to ensure its safe usage in a multithreading environment. This task will help you understand the importance of statelessness in services that are accessed by multiple threads concurrently.
## Objective
- Identify and refactor statefulness in a service to ensure thread safety.
## Requirements
- Java 8 or above.
- Basic understanding of Java concurrency and state management.
## Background
In multi-threaded environments, stateful services (services that maintain state across method calls or store data in fields) can lead to issues such as race conditions or data inconsistencies. Transforming a service to be stateless (not maintaining any shared state) ensures that it is free from such concerns.
## Initial Setup
You are provided with a class `UserProfileProcessor` that currently maintains a cache of user profiles as state:
```java
public class UserProfileProcessor {
    private Map<String, UserProfile> cache = new HashMap<>();
    public UserProfile getUserProfile(String userId) {
        if (cache.containsKey(userId)) {
            return cache.get(userId);
        }
        UserProfile profile = fetchProfileFromDatabase(userId);
        cache.put(userId, profile);
        return profile;
    }
    private UserProfile fetchProfileFromDatabase(String userId) {
        // Simulates fetching from the database
        return new UserProfile(userId, "Name_" + userId);
    }
}
```
## Task Details
#### Part 1: Analyze the Current Service
Identify why the UserProfileProcessor is considered stateful and why this can be problematic in a multithreading environment.
Part 2: Design a Stateless Solution
Outline a plan to refactor the UserProfileProcessor to be stateless. Consider:
Removing or altering how the shared state is managed.
Ensuring that methods do not rely on shared mutable states.
Part 3: Write Tests
Draft test scenarios that ensure the refactored service can handle concurrent accesses correctly. These tests should aim to uncover any data inconsistencies or race conditions.
Expected Outcome
The refactored UserProfileProcessor should:
Not maintain any shared state.
Handle concurrent requests without running into race conditions or data inconsistency issues.
Deliverables
A refactoring plan: Detailed outline on changes that will be made to make UserProfileProcessor stateless.
Test scenarios: Descriptions of tests that will validate the thread safety of the refactored service.
Conclusion
Refactoring this service to be stateless will make it robust and reliable when accessed in multi-threaded contexts. This task will enhance your understanding of state management and its impact on application behavior in concurrent settings.



# 4. Task: Exploring Java Concurrency with Atomic Variables and Concurrent Collections (25 points)
## Description
In this task, you will create a Java application to demonstrate the use and benefits of atomic variables and concurrent collections. This task will help you understand how to manage concurrency in Java effectively using these tools, which are designed to prevent thread interference and memory consistency errors.
## Objectives
1. Gain practical experience with atomic variables.
2. Explore the functionality and benefits of concurrent collections.
3. Understand and apply thread safety principles in a concurrent Java environment.
## Requirements
- Java 8 or above.
- IDE of choice (e.g., IntelliJ, Eclipse).
- Basic knowledge of Java threading and collections.
## Background
Java provides a rich set of synchronization tools, including atomic variables in the `java.util.concurrent.atomic` package and thread-safe collections in the `java.util.concurrent` package. These utilities are designed for cases where locks (synchronized methods or blocks) would be overly cumbersome or performance-impaired.
## Task Details
### Part 1: Working with Atomic Variables (10 points)
#### Task
Create a counter service that uses `AtomicInteger` and compare it with a non-synchronized integer counter in terms of thread safety and performance.
#### Implementation
1. Define a basic counter class using a plain integer with increment and get methods.
2. Define another counter class using `AtomicInteger`.
3. Create multiple threads to increment each counter a large number of times (e.g., 100,000 increments per thread, with at least 10 threads).
4. Measure and compare the final values of each counter after all threads complete, and note any discrepancies.
5. Measure the time taken for each approach.
### Part 2: Exploring Concurrent Collections (15 points)
#### Task
Demonstrate the use of `ConcurrentHashMap` by implementing a simple caching mechanism.
#### Implementation
1. Create a service that fetches a numeric value for a key, simulating a delay (e.g., using `Thread.sleep`) to mimic a time-consuming computation.
2. Cache the results using `ConcurrentHashMap` where keys are string identifiers and values are the computed numbers.
3. Access this service concurrently from multiple threads, ensuring that each thread tries to fetch values for the same set of keys, and check the consistency of the data returned.
### Part 3: Testing and Documentation
#### Testing
Write tests to ensure both components work as expected under concurrent access. For the atomic variable, ensure the counter's final count is as expected. For the concurrent collection, ensure no repeated computations for the same key and data consistency.
#### Documentation
Document the implementations and findings. Detail how atomic variables and concurrent collections help manage data consistency and concurrency.
## Deliverables
- Java source code for the counter service and caching service.
- A test suite that confirms the functionality and thread safety of both services.
- A comprehensive report on the performance comparisons and benefits of using atomic variables and concurrent collections.
## Expected Outcomes
- Improved understanding of atomic operations and their necessity in certain concurrent scenarios.
- Insights into how concurrent collections can simplify coding for concurrent scenarios while maintaining performance and correctness.
## Conclusion
This practical task is designed to strengthen your understanding of handling concurrency in Java using atomic classes and concurrent collections. By comparing these tools with non-synchronized approaches, you'll learn about both the potential pitfalls of improper synchronization and the performance implications of various synchronization techniques.

