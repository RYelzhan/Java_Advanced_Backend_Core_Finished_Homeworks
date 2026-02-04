package com.rakhiimzhanov.task3;

import java.util.concurrent.Semaphore;

public class ConnectionPoolExample {
    // Limits simultaneous access to 5 permits (connections)
    private final Semaphore semaphore = new Semaphore(5);

    public void useConnection(String threadName) {
        try {
            System.out.println(threadName + " is waiting to acquire a permit...");
            semaphore.acquire(); // Acquire a permit
            System.out.println(threadName + " acquired a permit and is using the connection.");

            // Simulate using the resource
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release(); // Release the permit in a finally block
            System.out.println(threadName + " released the permit.");
        }
    }

    public static void main(String[] args) {
        ConnectionPoolExample pool = new ConnectionPoolExample();
        for (int i = 1; i <= 10; i++) {
            final String threadName = "Thread-" + i;
            new Thread(() -> pool.useConnection(threadName)).start();
        }
    }
}
