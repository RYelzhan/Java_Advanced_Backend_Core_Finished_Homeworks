package com.epam.contest.lessonTwo.producerConsumer;

import java.util.concurrent.BlockingQueue;

class BlockingQueueConsumer extends Thread {

    private final BlockingQueue<Integer> sharedQueue;

    BlockingQueueConsumer(BlockingQueue<Integer> sharedQueue) {
        super("CONSUMER");
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        int item = 0;
        while (item != Integer.MIN_VALUE) {
            try {
                item = sharedQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + " consumed " + item);
        }
    }
}
