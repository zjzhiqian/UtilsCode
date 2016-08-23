package com.hzq.lock;


import java.util.concurrent.locks.ReentrantLock;

/**
 * AQSTest
 * Created by hzq on 16/8/17.
 */
public class AQSTest {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(true);

        lock.lock();
        new Thread(() -> {
            System.out.println("new Thread acquire lock ");
            lock.lock();
            System.out.println("new Thread finish  lock ");

        }).start();
        lock.unlock();

    }


}
