package com.epam.contest.lessonTwo.ownQueue;

public record Producer(OuhMyBlockingQueue queue) implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Producer -> I'm going to put new value!");
                queue.put(String.valueOf(i));
                System.out.println("Producer -> I'm going to sleep!");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
