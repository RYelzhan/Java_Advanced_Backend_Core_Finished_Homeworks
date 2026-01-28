package com.epam.contest.lessonThree.futures.extra;

import java.util.concurrent.*;

public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> cf = new CompletableFuture<>();

        System.out.println(cf.isDone());

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        var future = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            cf.complete("Go to the stars, boys");
            return cf.get();
        });

        System.out.println(future.get());

        executorService.shutdown();
    }
}
