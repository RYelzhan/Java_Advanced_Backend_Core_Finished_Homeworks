package com.epam.contest.lessonOne.threads;

public class ThreadExample {

    public static void main(String[] args) throws InterruptedException {
        var object = new ImportantObject();
        // methods:
//        object.wait();
//        object.notify();
//        object.notify();
        new Thread(new OuhMyRunneble()).start();

        // we can sleep
        Thread.sleep(1000);

        // processes.... (java 9+)
        ProcessHandle.allProcesses();
    }
}
