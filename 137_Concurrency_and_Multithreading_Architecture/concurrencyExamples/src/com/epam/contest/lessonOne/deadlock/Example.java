package com.epam.contest.lessonOne.deadlock;

public class Example {

    private final Object firstResource = new Object();
    private final Object secondResource = new Object();


    public void run() {
        new Thread(new FirstThread(firstResource, secondResource)).start();
        new Thread(new SecondThread(firstResource, secondResource)).start();
    }
}