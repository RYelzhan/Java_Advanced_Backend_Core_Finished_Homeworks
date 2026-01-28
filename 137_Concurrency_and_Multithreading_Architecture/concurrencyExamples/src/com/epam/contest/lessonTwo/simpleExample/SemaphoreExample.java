package com.epam.contest.lessonTwo.simpleExample;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    private Semaphore semaphore = new Semaphore(15, true);

    public void run() throws InterruptedException {
        // hard way:
        semaphore.acquire();
        semaphore.release();

        // base way:
        semaphore.tryAcquire();
        semaphore.acquire();
        semaphore.release();
    }
}
