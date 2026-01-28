package com.epam.contest.lessonOne.deadlock;

public record FirstThread(Object resourceA, Object resourceB) implements Runnable {
        
        @Override
        public void run() {
            calculate();
        }
        
        private void calculate() {
            synchronized (resourceA) {
                System.out.println("First was locked resourceA and tried to get resourceB");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resourceB) {
                    System.out.println("resourceB has class:" + resourceB.getClass());
                }
            }
        }
    }
    