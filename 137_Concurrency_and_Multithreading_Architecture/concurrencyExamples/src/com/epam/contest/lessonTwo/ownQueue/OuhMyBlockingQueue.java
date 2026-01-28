package com.epam.contest.lessonTwo.ownQueue;

class OuhMyBlockingQueue {

    private static final int defaultSize = 15;
    private final String[] array;
    private int size;

    public OuhMyBlockingQueue(int size) {
        if (size == 0)
            array = new String[defaultSize];
        else {
            this.size = size;
            array = new String[size];
        }
    }

    private int lastIndex = 0;
    private int firstIndex = 0;

    public synchronized void put(String element) throws InterruptedException {
        if (lastIndex == defaultSize) {
            wait();
        }
        int currentIndex = lastIndex++;
        array[currentIndex] = element;
        notifyAll();
    }
    
    public synchronized String get() throws InterruptedException {
        if (lastIndex == firstIndex) {
            wait();
        }
        String element = array[firstIndex++];
        lastIndex--;

        notify();
        return element;
    }

    private int getSize() {
        return size;
    }

    private void checkAndRefresh() {

    }
}
