package com.rakhiimzhanov.task1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        getConcurrentModificationException();
    }

    private static void getConcurrentModificationException() {
        Thread t1 = new Thread(OrdinaryMap::increaseCount);
        Thread t2 = new Thread(OrdinaryMap::sumTheValues);

        t1.start();
        t2.start();
    }
}

class OrdinaryMap {
//    private static final Map<Integer,Integer> map = new HashMap<>();
//    private static final Map<Integer,Integer> map = new ConcurrentHashMap<>();
    private static final Map<Integer,Integer> map = Collections.synchronizedMap(new HashMap<>());
    private static final Random rand = new Random();
    private static volatile boolean shouldStop = false;

    public static void increaseCount() {
        try {
            while (!shouldStop) {
                int key = rand.nextInt(5);
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Thread 1: Caught ConcurrentModificationException");
            shouldStop = true;
        }
        System.out.println("Thread 1: Stopped");
    }

    public static void sumTheValues() {
        try {
            while (!shouldStop) {
                int sum = 0;
                // Use explicit iteration instead of stream
                for (Integer value : map.values()) {
                    sum += value;
                    Thread.sleep(1);
                }

                System.out.println("Sum: " + sum);
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Thread 2: Caught ConcurrentModificationException");
            shouldStop = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Thread 2: Stopped");
    }
}
