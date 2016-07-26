package com.hzq.lock.lockSupport;

import java.util.Date;

/**
 * Created by hzq on 16/7/25.
 */
public class TestFIFOMutex {

    private static FIFOMutex mutex = new FIFOMutex();

    public static void main(String[] args) {
        new Thread(() -> {
            sleep(1000 * 2);
            System.out.println(new Date() + " :t1 准备lock");
            mutex.lock();
            System.out.println(new Date() + " :t1 完成lock");
        }).start();

        new Thread(() -> {
            sleep(1000 * 3);
            System.out.println(new Date() + " :t2 准备lock");
            mutex.lock();
            System.out.println(new Date() + " :t2 完成lock");
        }).start();


        new Thread(() -> {
            sleep(1000 * 5);
            System.out.println(new Date() + " :t3 准备 unlock释放");
            mutex.unlock();
            System.out.println(new Date() + " :t3 释放完毕unlock");
        }).start();
    }


    private static void sleep(long num) {
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
