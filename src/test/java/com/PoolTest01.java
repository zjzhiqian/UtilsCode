package com;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * PoolTest01
 * Created by hzq on 16/8/11.
 */
public class PoolTest01 {

    public static void main(String[] args) {


        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 3L, TimeUnit.DAYS, new ArrayBlockingQueue<>(3));

        executor.execute(()-> System.out.println(3));


        executor.shutdown(); //shutDown方法 将AtomicInteger的 ctl设置为 0||workerCountOf(c)


    }
}
