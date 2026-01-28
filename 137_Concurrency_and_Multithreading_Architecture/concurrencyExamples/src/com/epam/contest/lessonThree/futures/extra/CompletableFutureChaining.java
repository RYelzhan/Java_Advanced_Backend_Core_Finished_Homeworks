package com.epam.contest.lessonThree.futures.extra;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureChaining {

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "Satellite for dogs";
        }).thenApplyAsync(s -> {
            System.out.println(Thread.currentThread().getName());
            return s + ": Belka";
        }).thenApply(s -> {
            System.out.println(Thread.currentThread().getName());
            return s + " & Strelka";
        }).thenAcceptAsync(System.out::println);

        CompletableFuture.supplyAsync(() -> "Satellite with cats")
                .thenApplyAsync(s -> s + " + Meow")
                .thenApplyAsync(s -> s + " Meeooooooow")
                .thenRunAsync(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Nobody knows about heroic cats");
                });
    }
}
