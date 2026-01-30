package com.rakhiimzhanov.task4;

class BlockingQueueConsumer extends Thread {

    private final BlockingObjectPool sharedQueue;

    BlockingQueueConsumer(BlockingObjectPool sharedQueue) {
        super("CONSUMER");
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        Integer item = 0;
        while (item != Integer.MIN_VALUE) {
            try {
                item = (Integer) sharedQueue.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + " consumed " + item);
        }
    }
}
