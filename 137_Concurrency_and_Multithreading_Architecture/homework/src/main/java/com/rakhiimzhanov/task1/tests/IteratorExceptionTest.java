package com.rakhiimzhanov.task1.tests;

import com.rakhiimzhanov.task1.maps.ReadWriteLockMap;
import com.rakhiimzhanov.task1.maps.SynchronizedMap;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class IteratorExceptionTest {
    private static final int NUM_ITERATIONS = 1000;
    static volatile boolean shouldStop = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Testing ConcurrentModificationException with iterators\n");

        testWithIterator("UnsafeMap", new HashMap<>());
        testWithIterator("SynchronizedMap", new SynchronizedMap<>());
        testWithIterator("ReadWriteLockMap", new ReadWriteLockMap<>());
        testWithIterator("ConcurrentHashMap", new ConcurrentHashMap<>());
    }

    private static void testWithIterator(String name, Map<Integer, String> map)
            throws InterruptedException {
        System.out.println("Testing: " + name);

        // Pre-populate map
        for (int i = 0; i < 100; i++) {
            map.put(i, "value" + i);
        }

        AtomicInteger exceptions = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(2);

        // Thread 1: Modify the map
        Thread modifier = new Thread(() -> {
            try {
                for (int i = 0; i < NUM_ITERATIONS && !shouldStop; i++) {
                    map.put(i % 100, "modified" + i);
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                exceptions.incrementAndGet();
            } finally {
                latch.countDown();
            }
        });

        // Thread 2: Iterate over the map
        Thread iterator = new Thread(() -> {
            try {
                for (int i = 0; i < NUM_ITERATIONS && !shouldStop; i++) {
                    // This will throw ConcurrentModificationException on UnsafeMap
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        entry.getValue(); // Just access it
                    }
                    Thread.sleep(1);
                }
            } catch (ConcurrentModificationException e) {
                System.out.println("  ✗ ConcurrentModificationException caught!");
                exceptions.incrementAndGet();
                shouldStop = true;
            } catch (Exception e) {
                System.out.println("  ✗ Other exception: " + e.getClass().getSimpleName());
                exceptions.incrementAndGet();
            } finally {
                latch.countDown();
            }
        });

        modifier.start();
        iterator.start();
        latch.await();

        System.out.println("  Exceptions: " + exceptions.get());
        System.out.println("  Status: " + (exceptions.get() == 0 ? "✓ No exceptions" : "✗ Had exceptions"));
        System.out.println();
    }
}