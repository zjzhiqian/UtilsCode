package com.hzq.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 *
 * Created by hzq on 16/5/26.
 */
public class AndTest {

    @Test
    public void testAnd() {

        Random rand = new Random();
        for (int i = 0; i < 99999; i++) {
            Integer randVal = rand.nextInt();
            Integer val = 7 & randVal;
            System.out.println(val);
            Assert.assertTrue(val < 8 && val >= 0);
        }


    }

    @Test
    public void test02() {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>();

    }

}
