package com.epam.contest.lessonTwo.producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class SemaphoreSolution {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(0);
        Queue<Integer> sharedQueue = new LinkedList<>();

        SemaphoreProducer producer = new SemaphoreProducer(semaphore, sharedQueue);
        SemaphoreConsumer consumer = new SemaphoreConsumer(semaphore, sharedQueue);

        producer.start();
        consumer.start();
    }
}
