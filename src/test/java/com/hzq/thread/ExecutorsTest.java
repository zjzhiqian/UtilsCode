package com.hzq.thread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * ExecutorsTest
 * Created by hzq on 16/8/10.
 */
public class ExecutorsTest {
    private static final int COUNT_BITS = Integer.SIZE - 3;

    @Test
    public void test01() {
        //二进制 1是 00000000 00000000 00000000 000000001    正数反码的+1 = 负数
        //      -1是  11111111 11111111 11111111 11111111
        int running = -1 << 29;
        int capacity = (1 << 29) - 1;
        int stop = 1 << 29;
        String k = Integer.toBinaryString(running);  //running  11100000 00000000 00000000 00000000
        String j = Integer.toBinaryString(capacity); //capacity 00011111 11111111 11111111 11111111
        String s = Integer.toBinaryString(stop); //stop         10000000 00000000 00000000 00000000
        System.out.println("running:" + k);
        System.out.println("capacity:" + j);
        System.out.println("stop:" + s);
        //running && capacity = workingCount
        //running && ~capacity = runStateOf
        //running <0  = isRunning
    }


    public static void main(String[] args) {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, 15, TimeUnit.DAYS, new ArrayBlockingQueue<>(1));
//        executor.shutdown();
        //执行shutDown操作时, 1.ctl本来值为: 11100000 00000000 00000000 00000002
        //                如果ctl > 0 时,ctl不变,否则 ctl设置为workerCount
        //                  2.所有线程都进行interrupt


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 14, TimeUnit.DAYS, new LinkedBlockingDeque<>(), r -> {
            Thread t = new Thread();
//            t.setDaemon(true);
            return t;
        });
        threadPoolExecutor.prestartAllCoreThreads();


    }
}
