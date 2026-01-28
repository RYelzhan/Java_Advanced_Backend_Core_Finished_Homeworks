package com.epam.contest.lessonTwo.producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Producer Consumer Problem solution using {@link BlockingQueue} in Java.
 * {@link BlockingQueue} not only provide a data structure to store data
 * but also gives you flow control, require for inter thread communication.
 *
 * <p>{@link BlockingQueue} provides a {@link BlockingQueue#put(Object) put()} method to store the element
 * and {@link BlockingQueue#take() take()} method to retrieve the element.
 * Both are blocking method, which means {@link BlockingQueue#put(Object) put()} will block if the queue
 * has reached its capacity and there is no place to add a new element.
 * Similarly, {@link BlockingQueue#take() take()} method will block if blocking queue is empty.
 * So, you can see that critical requirement of the producer-consumer pattern is met right there,
 * you don't need to put any thread synchronization code.
 */
public class BlockingQueueSolution {

    public static void main(String[] args) {
        BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>(5);

        BlockingQueueProducer producer = new BlockingQueueProducer(sharedQueue);
        BlockingQueueConsumer consumer = new BlockingQueueConsumer(sharedQueue);

        producer.start();
        consumer.start();
    }
}
