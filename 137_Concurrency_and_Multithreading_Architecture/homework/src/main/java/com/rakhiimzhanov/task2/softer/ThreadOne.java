package com.rakhiimzhanov.task2.softer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;

public class ThreadOne implements Runnable {
    private final List<Integer> list;
    private final ReadWriteLock sharedReadWriteLock;
    private final Condition condition;

    public ThreadOne(List<Integer> list, ReadWriteLock sharedReadWriteLock, Condition condition) {
        this.list = list;
        this.sharedReadWriteLock = sharedReadWriteLock;
        this.condition = condition;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sharedReadWriteLock.writeLock().lock();

                // Add random number
                int number = random.nextInt(100); // Smaller numbers for readability
                list.add(number);
                System.out.println("Thread 1 - Added: " + number + " (List size: " + list.size() + ")");

                condition.signalAll(); // Wake up waiting threads

                condition.await(1000, java.util.concurrent.TimeUnit.MILLISECONDS);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                sharedReadWriteLock.writeLock().unlock();
            }
        }
        System.out.println("Thread 1 stopped.");
    }
}