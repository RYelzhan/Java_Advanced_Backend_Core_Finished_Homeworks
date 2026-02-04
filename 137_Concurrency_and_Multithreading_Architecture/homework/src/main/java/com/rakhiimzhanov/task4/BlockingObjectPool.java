package com.rakhiimzhanov.task4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Pool that blocks when it has not any items or it is full
 */
public class BlockingObjectPool {
    private final Object[] objects;
    private int count = 0;
    private final int capacity;

    /** Single lock for all operations */
    private final ReentrantLock lock = new ReentrantLock();

    /** Wait queue for waiting gets */
    private final Condition notEmpty = lock.newCondition();

    /** Wait queue for waiting takes (puts) */
    private final Condition notFull = lock.newCondition();

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        this.capacity = size;
        this.objects = new Object[size];
        this.count = 0;
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public Object get() throws InterruptedException {
        lock.lock();
        try {
            if (count == 0) {
                notEmpty.await();
            }

            Object item = objects[count - 1];
            objects[count - 1] = null;
            count--;

            notFull.signal(); // Signal waiting take() threads
//            if (count > 0) {
//                notEmpty.signal();
//            }

            return item;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool, can not be null
     */
    public void take(Object object) throws InterruptedException {
        if (object == null) {
            throw new NullPointerException();
        }

        lock.lock();
        try {
            if (count == capacity) {
                notFull.await();
            }

            objects[count] = object;
            count++;

            notEmpty.signal(); // Signal waiting get() threads
//            if (count < capacity) {
//                notFull.signal();
//            }
        } finally {
            lock.unlock();
        }
    }
}