package com.rakhiimzhanov.task1.tests;

import com.rakhiimzhanov.task1.maps.ReadWriteLockMap;
import com.rakhiimzhanov.task1.maps.SynchronizedMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapPerformanceTest {
    private static final int NUM_THREADS = 10;
    private static final int OPERATIONS_PER_THREAD = 100000;
    private static final int WARMUP_ITERATIONS = 2;
    private static final int TEST_ITERATIONS = 5;

    // Pre-generate operations to ensure all maps do the same work
    private static final Operation[] OPERATIONS = generateOperations();
    // low number = high contention AND high number (e.g 1000) = low contention
    private static final int CONTENTION_DECIDING_NUMBER = 10;

    static class Operation {
        final int key;
        final int value;
        final OperationType type;

        Operation(int key, int value, OperationType type) {
            this.key = key;
            this.value = value;
            this.type = type;
        }
    }

    enum OperationType {
        READ, WRITE, REMOVE
    }

    private static Operation[] generateOperations() {
        Random random = new Random(42); // Fixed seed for reproducibility
        int totalOps = NUM_THREADS * OPERATIONS_PER_THREAD;
        Operation[] ops = new Operation[totalOps];

        for (int i = 0; i < totalOps; i++) {
            int key = random.nextInt(CONTENTION_DECIDING_NUMBER);
            int value = random.nextInt();
            int opType = random.nextInt(10);

            OperationType type;
            if (opType < 7) {
                type = OperationType.READ;
            } else if (opType < 9) {
                type = OperationType.WRITE;
            } else {
                type = OperationType.REMOVE;
            }

            ops[i] = new Operation(key, value, type);
        }

        return ops;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("=".repeat(80));
        System.out.println("Total operations: " + OPERATIONS.length);
        System.out.println("Warmup iterations: " + WARMUP_ITERATIONS);
        System.out.println("Test iterations: " + TEST_ITERATIONS);
        System.out.println("=".repeat(80));
        System.out.println();

        // Test each map implementation
        testMapWithWarmup("UnsafeMap", new HashMap<>());
        testMapWithWarmup("SynchronizedMap", new SynchronizedMap<>());
        testMapWithWarmup("ReadWriteLockMap", new ReadWriteLockMap<>());
        testMapWithWarmup("ConcurrentHashMap", new ConcurrentHashMap<>());
    }

    private static void testMapWithWarmup(String name, Map<Integer, Integer> map) throws InterruptedException {
        System.out.println("Testing: " + name);

        // Warmup
        System.out.print("  Warming up");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            testMap(map);
            System.out.print(".");
        }
        System.out.println(" done");

        // Actual test - run multiple times and average
        long totalTime = 0;
        int finalSize = 0;

        for (int i = 0; i < TEST_ITERATIONS; i++) {
            long time = testMap(map);
            totalTime += time;
            finalSize = map.size();
        }

        long avgTime = totalTime / TEST_ITERATIONS;
        long totalOps = (long) NUM_THREADS * OPERATIONS_PER_THREAD;
        double opsPerSecond = (totalOps * 1000.0) / avgTime;

        System.out.println("  Average time: " + avgTime + " ms");
        System.out.println("  Operations/sec: " + String.format("%.2f", opsPerSecond));
        System.out.println("  Final map size: " + finalSize);
        System.out.println();
    }

    private static long testMap(Map<Integer, Integer> map) throws InterruptedException {
        long startTime;
        long endTime;
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(NUM_THREADS);

        int opsPerThread = OPERATIONS.length / NUM_THREADS;

        // Submit all tasks
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            final int startIdx = threadId * opsPerThread;
            final int endIdx = (threadId == NUM_THREADS - 1) ?
                    OPERATIONS.length : (threadId + 1) * opsPerThread;

            executor.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready

                    for (int j = startIdx; j < endIdx; j++) {
                        Operation op = OPERATIONS[j];
                        switch (op.type) {
                            case READ:
                                map.get(op.key);
                                break;
                            case WRITE:
                                map.put(op.key, op.value);
                                break;
                            case REMOVE:
                                map.remove(op.key);
                                break;
                        }
                    }
                } catch (Exception e) {
                    // Silently catch for performance test
                } finally {
                    endLatch.countDown();
                }
            });
        }

        // Start timing
        startTime = System.currentTimeMillis();
        startLatch.countDown(); // Release all threads at once
        endLatch.await(); // Wait for all to complete
        endTime = System.currentTimeMillis();

        executor.shutdown();

        return endTime - startTime;
    }

//    @FunctionalInterface
    interface MapSupplier {
        Map<Integer, Integer> get();
    }
}