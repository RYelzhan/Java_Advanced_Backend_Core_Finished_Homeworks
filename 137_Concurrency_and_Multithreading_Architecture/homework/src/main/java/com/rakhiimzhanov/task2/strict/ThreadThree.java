package com.rakhiimzhanov.task2.strict;

import java.util.List;

public class ThreadThree implements Runnable {
    private final List<Integer> list;

    public ThreadThree(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (list) {
                try {
                    list.wait(); // Wait for notification

                    // Calculate sum of squares
                    long sumOfSquares = 0;
                    for (Integer num : list) {
                        sumOfSquares += (long) num * num;
                    }

                    // Calculate and print square root
                    double result = Math.sqrt(sumOfSquares);
                    System.out.println("Thread 3 - Square root of sum of squares: " + result);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println("Thread 3 stopped.");
    }
}