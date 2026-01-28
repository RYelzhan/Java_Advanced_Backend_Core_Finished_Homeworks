package com.epam.contest.lessonThree.futures.extra;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomExecutorServiceExample {

    private static final String YURI_GAGARIN = "Yuri Gagarin";

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        CompletableFuture<Void> result = CompletableFuture.supplyAsync(() -> YURI_GAGARIN, executorService)
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> "German Titov"),
                        (s1, s2) -> pickYura(), executorService)
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> "Grigori Nelubov"), (s1, s2) -> pickYura())
                .thenApplyAsync(String::toUpperCase)
                .thenAcceptAsync(s -> System.out.println("First man in space is " + s), executorService);

        System.out.println(result.isDone());

        executorService.shutdown();
    }

    private static String pickYura() {
        return YURI_GAGARIN;
    }
}
