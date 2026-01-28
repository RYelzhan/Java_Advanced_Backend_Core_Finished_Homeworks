package com.rakhiimzhanov.task2.strict;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Shared list - can be non-synchronised collection, as we use other synchronisation tools
        List<Integer> sharedList = new ArrayList<>();

        // Create thread instances
        ThreadOne threadOne = new ThreadOne(sharedList);
        ThreadTwo threadTwo = new ThreadTwo(sharedList);
        ThreadThree threadThree = new ThreadThree(sharedList);

        // Start threads
        Thread t1 = new Thread(threadOne, "Thread-1-Producer");
        Thread t2 = new Thread(threadTwo, "Thread-2-Sum");
        Thread t3 = new Thread(threadThree, "Thread-3-SquareRoot");

        t1.start();
        t2.start();
        t3.start();

        // Let it run for some time, then stop (optional)
        try {
            Thread.sleep(10000); // Run for 10 seconds

            // Interrupt all threads to stop them gracefully
            t1.interrupt();
            t2.interrupt();
            t3.interrupt();

            // Wait for threads to finish
            t1.join();
            t2.join();
            t3.join();

            System.out.println("All threads stopped.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}