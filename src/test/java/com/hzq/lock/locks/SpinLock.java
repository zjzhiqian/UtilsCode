package com.hzq.lock.locks;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * Created by hzq on 17/5/18.
 */
public class SpinLock {


    private volatile AtomicReference<Thread> atom = new AtomicReference<>();

    public void lock() {

        Thread thread = Thread.currentThread();
        while (!atom.compareAndSet(null, thread)) {
        }
    }


    public void unlock() {
        Thread thread = Thread.currentThread();
        atom.compareAndSet(thread, null);
    }

}
