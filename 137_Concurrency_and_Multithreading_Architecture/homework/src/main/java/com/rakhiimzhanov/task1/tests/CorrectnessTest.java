package com.rakhiimzhanov.task1.tests;

import com.rakhiimzhanov.task1.maps.ReadWriteLockMap;
import com.rakhiimzhanov.task1.maps.SynchronizedMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CorrectnessTest {
    private static final int NUM_THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 10_000;
    private static final String KEY = "counter";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("=".repeat(80));
        System.out.println("\nTesting correctness with counter increments...\n");

        testCounterCorrectness("UnsafeMap", new HashMap<>());
        testCounterCorrectness("SynchronizedMap", new SynchronizedMap<>());
        testCounterCorrectness("ReadWriteLockMap", new ReadWriteLockMap<>());
        testCounterCorrectness("ConcurrentHashMap", new ConcurrentHashMap<>());
    }

    private static void testCounterCorrectness(String name, Map<String, Integer> map)
            throws InterruptedException {
        System.out.println("Testing: " + name);

        map.put(KEY, 0);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch latch = new CountDownLatch(NUM_THREADS);
        AtomicInteger exceptions = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                        map.compute(KEY, (k, v) -> v == null ? 1 : v + 1);
                    }
                } catch (Exception e) {
                    exceptions.incrementAndGet();
                    System.err.println("  Exception caught: " + e.getClass().getSimpleName());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        long duration = System.currentTimeMillis() - startTime;
        executor.shutdown();

        int expectedValue = NUM_THREADS * INCREMENTS_PER_THREAD;
        Integer actualValue = map.get(KEY);
        int lostUpdates = expectedValue - (actualValue != null ? actualValue : 0);
        double lossPercentage = (lostUpdates * 100.0) / expectedValue;

        System.out.println("  Time: " + duration + " ms");
        System.out.println("  Expected value: " + expectedValue);
        System.out.println("  Actual value: " + actualValue);
        System.out.println("  Lost updates: " + lostUpdates + " (" +
                String.format("%.2f%%", lossPercentage) + ")");
        System.out.println("  Exceptions: " + exceptions.get());
        System.out.println("  Status: " + (lostUpdates == 0 ? "✓ CORRECT" : "✗ DATA CORRUPTION"));
        System.out.println();
    }
}