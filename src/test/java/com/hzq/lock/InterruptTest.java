package com.hzq.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hzq on 16/5/10.
 */

class BlockedMutex {
    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
        lock.lock();
    }

    public void f() {
        try {
            lock.lockInterruptibly();
            System.out.println("lock aquired in f()");
        } catch (InterruptedException e) {
            System.out.println("interrupted from lock acquirsition f()");
        }
    }

}


class Blocked2 implements Runnable {
    BlockedMutex blockedMutex = new BlockedMutex();

    @Override
    public void run() {
        System.out.println("waiting for f() in blockedMutex");
        blockedMutex.f();
        System.out.println("broken out from block call");
    }
}

public class InterruptTest {


    public static void main(String... args) throws Exception {
        Thread t = new Thread(new Blocked2());
        t.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Issuing t.interrupt()");
        t.interrupt();
    }


}
