package com.epam.contest.lessonTwo.currentQueue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    protected BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            for (int i=0; i < 1000; i++) {
                System.out.println("Producer -> I'm going to add new element:" + i);
                queue.put(Integer.toString(i));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
