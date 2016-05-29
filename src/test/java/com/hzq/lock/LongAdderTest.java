package com.hzq.lock;

import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder Test
 * Created by hzq on 16/5/27.
 */
public class LongAdderTest {

    @Test
    public void test01(){
        final LongAdder longAdder = new LongAdder();
        longAdder.add(3);
        longAdder.decrement();
        System.out.println(longAdder.intValue());
    }
}
