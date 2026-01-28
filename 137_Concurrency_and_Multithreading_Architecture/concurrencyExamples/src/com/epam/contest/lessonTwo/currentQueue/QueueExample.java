package com.epam.contest.lessonTwo.currentQueue;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueExample {

    public static void main(String[] args) throws InterruptedException {
        var queue = new ArrayBlockingQueue<String>(1024);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(consumer).start();
        Thread.sleep(40000);
    }
}
