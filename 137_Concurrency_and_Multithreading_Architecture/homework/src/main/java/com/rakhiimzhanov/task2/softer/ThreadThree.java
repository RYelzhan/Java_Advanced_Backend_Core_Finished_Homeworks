package com.rakhiimzhanov.task2.softer;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;

public class ThreadThree implements Runnable {
    private final List<Integer> list;
    private final ReadWriteLock sharedReadWriteLock;
    private final Condition condition;

    public ThreadThree(List<Integer> list, ReadWriteLock sharedReadWriteLock, Condition condition) {
        this.list = list;
        this.sharedReadWriteLock = sharedReadWriteLock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            sharedReadWriteLock.writeLock().lock(); // Need write lock to use condition
            try {
                condition.await(); // Wait for signal

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
            } finally {
                sharedReadWriteLock.writeLock().unlock();
            }
        }
        System.out.println("Thread 3 stopped.");
    }
}