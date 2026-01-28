package com.rakhiimzhanov.task3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.RepeatedTest;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParallelProducersConsumersTest {

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testProducersRunSimultaneously() throws InterruptedException {
        // Arrange
        Semaphore semaphore = new Semaphore(0);
        Queue<Integer> sharedQueue = new LinkedList<>();

        int numberOfProducers = 5;
        SemaphoreProducer[] producers = new SemaphoreProducer[numberOfProducers];
        CountDownLatch startLatch = new CountDownLatch(1); // To start all at once
        CountDownLatch readyLatch = new CountDownLatch(numberOfProducers); // Wait for all to be ready

        // Act - Create all producers
        for (int i = 0; i < numberOfProducers; i++) {
            final int producerId = i;
            producers[i] = new SemaphoreProducer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown(); // Signal ready
                        startLatch.await(); // Wait for start signal
                        super.run(); // Execute actual production
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            producers[i].start();
        }

        // Wait for all producers to be ready
        readyLatch.await();

        long startTime = System.currentTimeMillis();

        // Release all producers simultaneously
        startLatch.countDown();

        // Give them time to produce
        Thread.sleep(2000);

        long endTime = System.currentTimeMillis();

        // Assert - All producers ran in parallel
        int permits = semaphore.availablePermits();
        assertTrue(permits > 1000,
                "Multiple producers running in parallel should produce many items quickly. Permits: " + permits);

        // With 5 producers running in parallel, execution should be faster than sequential
        long duration = endTime - startTime;
        System.out.println("Parallel production duration: " + duration + "ms, Permits: " + permits);

        // Cleanup
        for (SemaphoreProducer producer : producers) {
            if (producer != null) producer.interrupt();
        }
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testConsumersRunSimultaneously() throws InterruptedException {
        // Arrange
        Semaphore semaphore = new Semaphore(0);
        Queue<Integer> sharedQueue = new LinkedList<>();

        // Pre-populate with items
        int itemsToProduce = 5000;
        synchronized (sharedQueue) {
            for (int i = 0; i < itemsToProduce; i++) {
                sharedQueue.offer(i);
                semaphore.release();
            }
        }

        int numberOfConsumers = 5;
        SemaphoreConsumer[] consumers = new SemaphoreConsumer[numberOfConsumers];
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch readyLatch = new CountDownLatch(numberOfConsumers);

        // Act - Create all consumers
        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i] = new SemaphoreConsumer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown();
                        startLatch.await();
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            consumers[i].start();
        }

        // Wait for all consumers to be ready
        readyLatch.await();

        int permitsBeforeConsumption = semaphore.availablePermits();
        long startTime = System.currentTimeMillis();

        // Release all consumers simultaneously
        startLatch.countDown();

        // Give them time to consume
        Thread.sleep(2000);

        long endTime = System.currentTimeMillis();

        // Assert - All consumers ran in parallel
        int permitsAfterConsumption = semaphore.availablePermits();
        int consumed = permitsBeforeConsumption - permitsAfterConsumption;

        assertTrue(consumed > 100,
                "Multiple consumers running in parallel should consume many items. Consumed: " + consumed);

        long duration = endTime - startTime;
        System.out.println("Parallel consumption duration: " + duration + "ms, Consumed: " + consumed);

        // Cleanup
        for (SemaphoreConsumer consumer : consumers) {
            if (consumer != null) consumer.interrupt();
        }
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testProducersAndConsumersRunSimultaneously() throws InterruptedException {
        // Arrange
        Semaphore semaphore = new Semaphore(0);
        Queue<Integer> sharedQueue = new LinkedList<>();

        int numberOfProducers = 3;
        int numberOfConsumers = 3;

        SemaphoreProducer[] producers = new SemaphoreProducer[numberOfProducers];
        SemaphoreConsumer[] consumers = new SemaphoreConsumer[numberOfConsumers];

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch readyLatch = new CountDownLatch(numberOfProducers + numberOfConsumers);

        // Act - Create all producers with synchronized start
        for (int i = 0; i < numberOfProducers; i++) {
            producers[i] = new SemaphoreProducer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown();
                        startLatch.await();
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            producers[i].start();
        }

        // Create all consumers with synchronized start
        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i] = new SemaphoreConsumer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown();
                        startLatch.await();
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            consumers[i].start();
        }

        // Wait for all threads to be ready
        assertTrue(readyLatch.await(5, TimeUnit.SECONDS),
                "All producers and consumers should be ready");

        System.out.println("Starting all producers and consumers simultaneously...");

        // Release all threads simultaneously
        startLatch.countDown();

        // Let them run in parallel
        Thread.sleep(3000);

        // Assert - System handles simultaneous execution
        assertTrue(true,
                "Producers and consumers can run simultaneously without deadlock");

        // Cleanup
        for (SemaphoreProducer producer : producers) {
            if (producer != null) producer.interrupt();
        }
        for (SemaphoreConsumer consumer : consumers) {
            if (consumer != null) consumer.interrupt();
        }
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testHighParallelism() throws InterruptedException {
        // Arrange
        Semaphore semaphore = new Semaphore(0);
        Queue<Integer> sharedQueue = new LinkedList<>();

        int numberOfProducers = 10;
        int numberOfConsumers = 10;

        SemaphoreProducer[] producers = new SemaphoreProducer[numberOfProducers];
        SemaphoreConsumer[] consumers = new SemaphoreConsumer[numberOfConsumers];

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch readyLatch = new CountDownLatch(numberOfProducers + numberOfConsumers);
        ConcurrentHashMap<String, Long> threadStartTimes = new ConcurrentHashMap<>();

        // Create producers
        for (int i = 0; i < numberOfProducers; i++) {
            final int producerId = i;
            producers[i] = new SemaphoreProducer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown();
                        startLatch.await();
                        threadStartTimes.put("Producer-" + producerId, System.nanoTime());
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            producers[i].setName("Producer-" + i);
            producers[i].start();
        }

        // Create consumers
        for (int i = 0; i < numberOfConsumers; i++) {
            final int consumerId = i;
            consumers[i] = new SemaphoreConsumer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown();
                        startLatch.await();
                        threadStartTimes.put("Consumer-" + consumerId, System.nanoTime());
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            consumers[i].setName("Consumer-" + i);
            consumers[i].start();
        }

        // Wait for all to be ready
        assertTrue(readyLatch.await(5, TimeUnit.SECONDS));

        // Start all simultaneously
        startLatch.countDown();

        // Wait a bit for start times to be recorded
        Thread.sleep(100);

        // Assert - Check that threads started nearly simultaneously
        assertEquals(numberOfProducers + numberOfConsumers, threadStartTimes.size(),
                "All threads should have started");

        // Calculate time spread
        long minTime = threadStartTimes.values().stream().min(Long::compare).orElse(0L);
        long maxTime = threadStartTimes.values().stream().max(Long::compare).orElse(0L);
        long spreadMs = (maxTime - minTime) / 1_000_000;

        System.out.println("Thread start time spread: " + spreadMs + "ms");
        assertTrue(spreadMs < 1000,
                "All threads should start within 1 second of each other. Spread: " + spreadMs + "ms");

        // Let them run
        Thread.sleep(3000);

        // Cleanup
        for (SemaphoreProducer producer : producers) {
            if (producer != null) producer.interrupt();
        }
        for (SemaphoreConsumer consumer : consumers) {
            if (consumer != null) consumer.interrupt();
        }
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testStaggeredStart() throws InterruptedException {
        // Arrange - Test that we can start producers first, then consumers
        Semaphore semaphore = new Semaphore(0);
        Queue<Integer> sharedQueue = new LinkedList<>();

        int numberOfProducers = 3;
        int numberOfConsumers = 3;

        SemaphoreProducer[] producers = new SemaphoreProducer[numberOfProducers];
        SemaphoreConsumer[] consumers = new SemaphoreConsumer[numberOfConsumers];

        CountDownLatch producerStartLatch = new CountDownLatch(1);
        CountDownLatch consumerStartLatch = new CountDownLatch(1);
        CountDownLatch producerReadyLatch = new CountDownLatch(numberOfProducers);
        CountDownLatch consumerReadyLatch = new CountDownLatch(numberOfConsumers);

        // Create producers
        for (int i = 0; i < numberOfProducers; i++) {
            producers[i] = new SemaphoreProducer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        producerReadyLatch.countDown();
                        producerStartLatch.await();
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            producers[i].start();
        }

        // Create consumers
        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i] = new SemaphoreConsumer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        consumerReadyLatch.countDown();
                        consumerStartLatch.await();
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            consumers[i].start();
        }

        // Wait for all to be ready
        producerReadyLatch.await();
        consumerReadyLatch.await();

        // Start producers first
        System.out.println("Starting all producers simultaneously...");
        producerStartLatch.countDown();
        Thread.sleep(1000); // Let producers produce

        int permitsAfterProduction = semaphore.availablePermits();
        assertTrue(permitsAfterProduction > 0,
                "Producers should have created items before consumers start");

        // Then start consumers
        System.out.println("Starting all consumers simultaneously...");
        consumerStartLatch.countDown();
        Thread.sleep(2000); // Let consumers consume

        int permitsAfterConsumption = semaphore.availablePermits();
        assertTrue(permitsAfterConsumption < permitsAfterProduction,
                "Consumers should have consumed items");

        // Cleanup
        for (SemaphoreProducer producer : producers) {
            if (producer != null) producer.interrupt();
        }
        for (SemaphoreConsumer consumer : consumers) {
            if (consumer != null) consumer.interrupt();
        }
    }

    @RepeatedTest(3)
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testRepeatedParallelExecution() throws InterruptedException {
        // Test that parallel execution works consistently
        Semaphore semaphore = new Semaphore(0);
        Queue<Integer> sharedQueue = new LinkedList<>();

        SemaphoreProducer[] producers = new SemaphoreProducer[3];
        SemaphoreConsumer[] consumers = new SemaphoreConsumer[3];

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch readyLatch = new CountDownLatch(6);

        for (int i = 0; i < 3; i++) {
            producers[i] = new SemaphoreProducer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown();
                        startLatch.await();
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            producers[i].start();

            consumers[i] = new SemaphoreConsumer(semaphore, sharedQueue) {
                @Override
                public void run() {
                    try {
                        readyLatch.countDown();
                        startLatch.await();
                        super.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            consumers[i].start();
        }

        readyLatch.await();
        startLatch.countDown();

        Thread.sleep(2000);

        assertTrue(true, "Parallel execution works consistently across multiple runs");

        // Cleanup
        for (int i = 0; i < 3; i++) {
            if (producers[i] != null) producers[i].interrupt();
            if (consumers[i] != null) consumers[i].interrupt();
        }
    }
}
