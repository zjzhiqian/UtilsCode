package com.hzq.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hzq on 16/5/16.
 */
public class ReentrantLockTest {


    public static void main(String[] args){


        Lock l = new ReentrantLock();

        new Thread(l::lock).start();



        new Thread(l::lock).start();






    }
}
