package com.epam.contest.lessonThree.forkJoinPool;

import java.util.concurrent.ForkJoinPool;

import static com.epam.contest.lessonThree.forkJoinPool.FibonacciTask.fibLoop;
import static com.epam.contest.lessonThree.forkJoinPool.FibonacciTask.fibRecur;


public class FibonacciTaskExample {

    public static void main(String[] args) {
        assert new ForkJoinPool().invoke(new FibonacciTask(10)) == 55L;
        assert fibRecur(10) == 55L;
        assert fibLoop(10) == 55L;
    }
}