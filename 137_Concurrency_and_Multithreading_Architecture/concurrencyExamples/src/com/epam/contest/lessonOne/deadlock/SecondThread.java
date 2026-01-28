package com.epam.contest.lessonOne.deadlock;

public record SecondThread(Object resourceA, Object resourceB) implements Runnable {

        @Override
        public void run() {
            calculate();
        }

        private void calculate() {
            synchronized (resourceB) {
                System.out.println("Second was locked resourceB and tried to get resourceA");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (resourceA) {
                    System.out.println("resourceA has class:" + resourceA.getClass());
                }
            }
        }
    }