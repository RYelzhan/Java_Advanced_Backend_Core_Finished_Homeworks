package com.epam.contest.lessonThree.futures.extra;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SupplyAsyncExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "Satellite";
        });

        System.out.println(cf.get());

        CompletableFuture<Integer> speed = CompletableFuture.supplyAsync(() -> 1)
                .thenApplyAsync(x -> x * 7)
                .thenApply(x -> x + 1);

        System.out.println(speed.get());
    }

}
