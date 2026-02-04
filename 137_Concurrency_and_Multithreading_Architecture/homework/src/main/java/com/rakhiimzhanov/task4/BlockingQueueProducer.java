package com.rakhiimzhanov.task4;

class BlockingQueueProducer extends Thread {

    private static final int AMOUNT = 100;

    private final BlockingObjectPool sharedQueue;

    BlockingQueueProducer(BlockingObjectPool sharedQueue) {
        super("PRODUCER");
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        // no synchronization needed!
        for (int i = 0; i < AMOUNT; i++) {
            System.out.println(getName() + " produced " + i);
            try {
                sharedQueue.take(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            sharedQueue.take(Integer.MIN_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
