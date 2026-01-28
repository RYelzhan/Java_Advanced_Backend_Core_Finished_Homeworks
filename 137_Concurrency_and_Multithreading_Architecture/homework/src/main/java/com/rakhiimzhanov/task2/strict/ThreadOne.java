package com.rakhiimzhanov.task2.strict;

import java.util.List;
import java.util.Random;

public class ThreadOne implements Runnable {
    private final List<Integer> list;

    public ThreadOne(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (list) {
                try {
                    // Add random number
                    int number = random.nextInt(100); // Smaller numbers for readability
                    list.add(number);
                    System.out.println("Thread 1 - Added: " + number + " (List size: " + list.size() + ")");

                    // Notify waiting threads
                    list.notifyAll();

                    // Wait a bit before adding next number
                    list.wait(1000); // Wait 1 second or until notified

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println("Thread 1 stopped.");
    }
}