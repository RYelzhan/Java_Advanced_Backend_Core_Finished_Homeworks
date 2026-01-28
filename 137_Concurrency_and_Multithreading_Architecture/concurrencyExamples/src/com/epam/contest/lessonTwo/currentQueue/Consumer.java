package com.epam.contest.lessonTwo.currentQueue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    protected BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
          for (;;) {
               System.out.println("Consumer -> take element and sleep:" + queue.take());
               Thread.sleep(1000);
           }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}