package com.hzq.generic;

import org.junit.Test;

import java.nio.channels.Pipe;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hzq on 16/6/30.
 */
public class GenericTest01 {

    @Test
    public void test01() {
        final HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        System.out.println(1);
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Long g = 3L;
        System.out.println((a + b) == c);
        System.out.println((a + b) == g);
    }

}
