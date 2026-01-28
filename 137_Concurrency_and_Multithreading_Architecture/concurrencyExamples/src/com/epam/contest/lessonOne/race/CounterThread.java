package com.epam.contest.lessonOne.race;

record CounterThread(Counter counter, int increment) implements Runnable {

    private static final int ITERATIONS = 10;

    @Override
    public void run() {
        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println(Thread.currentThread() + ": " + counter.add(increment));
        }
    }
}
