package com.rakhiimzhanov.task3;

import java.util.Queue;
import java.util.concurrent.Semaphore;

class SemaphoreProducer extends Thread {

    private static final int AMOUNT = 10_000;

    private final Semaphore semaphore;

    private final Queue<Integer> sharedQueue;

    SemaphoreProducer(Semaphore semaphore, Queue<Integer> sharedQueue) {
        this.semaphore = semaphore;
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < AMOUNT; i++) {
            synchronized (sharedQueue) {
                sharedQueue.offer(i);
            }
            semaphore.release();
            System.out.println(getName() + " produced " + i + " (" + semaphore.availablePermits() + ")");
        }
        synchronized (sharedQueue) {
            sharedQueue.offer(Integer.MIN_VALUE);
        }
        semaphore.release();
    }
}
