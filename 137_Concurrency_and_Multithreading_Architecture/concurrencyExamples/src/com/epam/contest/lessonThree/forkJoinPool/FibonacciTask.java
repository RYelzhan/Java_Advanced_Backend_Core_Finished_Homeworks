package com.epam.contest.lessonThree.forkJoinPool;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Long> {

    /**
     * The minimum granularity input number length below which a parallel
     * Fibonacci computation algorithm will not further partition
     * the computation task.
     * Using smaller sizes typically results in memory contention across
     * tasks that makes parallel speedups unlikely.
     */
    private static final int MIN_PARALLEL_NUMBER = 10;

    private final int number;

    FibonacciTask(int number) {
        this.number = number;
    }

    @Override
    protected Long compute() {
        if (number <= MIN_PARALLEL_NUMBER) {
            return fibLoop(number);
        }
        FibonacciTask forkTask = new FibonacciTask(number - 1);
        forkTask.fork();
        FibonacciTask mainTask = new FibonacciTask(number - 2);
        return mainTask.compute() + forkTask.join();
    }

    static long fibRecur(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibRecur(n - 2) + fibRecur(n - 1);
        }
    }

    static long fibLoop(int n) {
        long result = 1;
        long prev = 0;
        for (int i = 1; i < n; i++) {
            long temp = result;
            result += prev;
            prev = temp;
        }
        return result;
    }
}
