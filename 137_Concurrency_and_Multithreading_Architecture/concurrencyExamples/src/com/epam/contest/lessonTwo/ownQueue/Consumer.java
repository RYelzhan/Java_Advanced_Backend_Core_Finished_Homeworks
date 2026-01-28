package com.epam.contest.lessonTwo.ownQueue;

public record Consumer(OuhMyBlockingQueue queue) implements Runnable {

    @Override
    public void run() {
        for (; ; ) {
            try {
                System.out.println("Consumer -> I'm going to get new value!");
                var value = queue.get();
                System.out.println("Consumer -> new value:" + value);
                System.out.println("Consumer -> I need to have a rest a little bit...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
