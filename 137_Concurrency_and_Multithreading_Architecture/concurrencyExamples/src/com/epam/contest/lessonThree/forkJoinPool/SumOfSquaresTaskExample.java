package com.epam.contest.lessonThree.forkJoinPool;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class SumOfSquaresTaskExample {

    private static final double[] ARRAY = new double[100_000_000];

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < ARRAY.length; i++) {
            ARRAY[i] = random.nextDouble();
        }

        var exampleClass = new Example();
        exampleClass.testForkJoinSumOfSquares();
        exampleClass.testLinearSumOfSquares();
    }

    private static class Example {

        public void testForkJoinSumOfSquares() {
            ForkJoinPool pool = new ForkJoinPool();
            SumOfSquaresTask task = new SumOfSquaresTask(ARRAY, 0, ARRAY.length, null);
            pool.invoke(task);
            System.out.println(task.getResult());
        }

        public void testLinearSumOfSquares() {
            double sum = 0;
            for (double v : ARRAY) {
                sum += v * v;
            }
            System.out.println(sum);
        }
    }
}