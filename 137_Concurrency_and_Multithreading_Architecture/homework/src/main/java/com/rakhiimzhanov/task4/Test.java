package com.rakhiimzhanov.task4;

public class Test {
    public static void main(String[] args) {
        BlockingObjectPool blockingObjectPool = new BlockingObjectPool(5);

        BlockingQueueProducer producer = new BlockingQueueProducer(blockingObjectPool);
        BlockingQueueConsumer consumer = new BlockingQueueConsumer(blockingObjectPool);

        producer.start();
        consumer.start();
    }
}
