package com.epam.contest.lessonTwo.ownQueue;

public class QueueExample {
    public static void main(String[] args) {
        var cl = new OuhMyBlockingQueue(0);
        new Thread(new Producer(cl)).start();
        new Thread(new Consumer(cl)).start();
    }
}
