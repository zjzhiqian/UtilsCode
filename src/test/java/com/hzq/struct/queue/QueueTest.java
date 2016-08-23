package com.hzq.struct.queue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * QueueTest
 * Created by hzq on 16/8/16.
 */
public class QueueTest {

    @Test
    public void test01() {

        ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(2);
        queue.add(3);
//        queue.add(4);
//        queue.add(5);

        queue.poll();
    }


}
