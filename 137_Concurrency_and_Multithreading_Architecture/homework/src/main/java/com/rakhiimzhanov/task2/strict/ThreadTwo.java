package com.rakhiimzhanov.task2.strict;

import java.util.List;

public class ThreadTwo implements Runnable {
    private final List<Integer> list;

    public ThreadTwo(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (list) {
                try {
                    list.wait(); // Wait for notification
//                    Thread.sleep(new Random().nextInt(50)); // Random delay

                    long sum = 0;
                    for (Integer integer : list) {
                        sum += integer;
                    }

                    System.out.println("Thread 2 - Sum: " + sum);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println("Thread 2 stopped.");
    }
}