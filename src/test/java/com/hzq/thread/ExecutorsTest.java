package com.hzq.thread;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

        String k = Integer.toBinaryString(running);  //running
        String j = Integer.toBinaryString(capacity); //capacity
        String s = Integer.toBinaryString(stop); //stop
        System.out.println("running:" + k);
        System.out.println("capacity:" + j);
        System.out.println("stop:" + s);


        System.out.println(Integer.toBinaryString(0 << COUNT_BITS));
        //running && capacity = workingCount

        //isRunning running<0就是running

        //runStateOf running & ~capacity

        System.out.println(Integer.toBinaryString(running & ~capacity));

    }


    public static void main(String[] args) {


        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 15, TimeUnit.DAYS, new ArrayBlockingQueue<>(15));
        executor.execute(() -> System.out.println("1"));


        System.out.println("kkk");
        executor.execute(() -> System.out.println("2"));

        System.out.println(COUNT_BITS);
        int tmp = -1 << COUNT_BITS;



    }
}
