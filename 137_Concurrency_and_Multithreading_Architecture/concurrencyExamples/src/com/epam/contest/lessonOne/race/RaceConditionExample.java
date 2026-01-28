package com.epam.contest.lessonOne.race;

public class RaceConditionExample {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread thread1 = new Thread(new CounterThread(counter, 2), "thread-1");
        Thread thread2 = new Thread(new CounterThread(counter, 3), "thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
