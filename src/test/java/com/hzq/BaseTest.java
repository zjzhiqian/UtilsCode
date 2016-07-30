package com.hzq;


import org.junit.Test;

import java.util.function.Function;

/**
 * BaseTest
 * Created by hzq on 16/6/9.
 */
public class BaseTest {

    @Test
    public void test01() {
        final Function<String, Integer> strToInt = String::length;

        final String s = new StringBuilder().append("1").append("3").toString();
        final String s1 = new StringBuffer().append("3").append("3223").toString();
    }

}
