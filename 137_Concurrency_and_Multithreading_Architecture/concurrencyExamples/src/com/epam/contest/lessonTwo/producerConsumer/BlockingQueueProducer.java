package com.epam.contest.lessonTwo.producerConsumer;

import java.util.concurrent.BlockingQueue;

class BlockingQueueProducer extends Thread {

    private static final int AMOUNT = 100;

    private final BlockingQueue<Integer> sharedQueue;

    BlockingQueueProducer(BlockingQueue<Integer> sharedQueue) {
        super("PRODUCER");
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        // no synchronization needed!
        for (int i = 0; i < AMOUNT; i++) {
            System.out.println(getName() + " produced " + i);
            try {
                sharedQueue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            sharedQueue.put(Integer.MIN_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
