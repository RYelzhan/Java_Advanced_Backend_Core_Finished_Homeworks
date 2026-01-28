package com.epam.contest.lessonTwo.producerConsumer;

import java.util.Queue;
import java.util.concurrent.Semaphore;

import static java.util.Optional.ofNullable;

class SemaphoreConsumer extends Thread {

    private final Semaphore semaphore;

    private final Queue<Integer> sharedQueue;

    SemaphoreConsumer(Semaphore semaphore, Queue<Integer> sharedQueue) {
        this.semaphore = semaphore;
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        int item = 0;
        while (item != Integer.MIN_VALUE) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (sharedQueue) {
                item = ofNullable(sharedQueue.poll()).orElse(0);
            }
            System.out.println(getName() + " consumed " + item + " (" + semaphore.availablePermits() + ")");
        }
    }
}
