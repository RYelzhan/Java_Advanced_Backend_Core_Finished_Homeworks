package com.rakhiimzhanov.task2.softer;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;

public class ThreadTwo implements Runnable {
    private final List<Integer> list;
    private final ReadWriteLock sharedReadWriteLock;
    private final Condition condition;

    public ThreadTwo(List<Integer> list, ReadWriteLock sharedReadWriteLock, Condition condition) {
        this.list = list;
        this.sharedReadWriteLock = sharedReadWriteLock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            sharedReadWriteLock.writeLock().lock();
            try {
                condition.await();

                if (!list.isEmpty()) {
                    long sum = 0;
                    for (Integer integer : list) {
                        sum += integer;
                    }
                    System.out.println("Thread 2 - Sum: " + sum);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                sharedReadWriteLock.writeLock().unlock();
            }
        }
        System.out.println("Thread 2 stopped.");
    }
}