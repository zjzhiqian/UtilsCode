package com.hzq.test;

/**
 * Integer >127 or <-128 用equals 或者intValue进行比较
 * Created by hzq on 16/5/29.
 */
public class IntegerTest {

    public static void main(String... args) {
        Integer a = 333;
        Integer b = 333;
        System.out.println(a == b);
        System.out.println(a.equals(b));
        Integer c = 127;
        Integer d = 127;
        System.out.println(c == d);
        System.out.println(a.intValue() == b);
    }
}
