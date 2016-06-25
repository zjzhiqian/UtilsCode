package com.hzq.test;

/**
 * CInitTest
 * Created by hzq on 16/6/25.
 */
public class CInitTest {

    static {
        i = 0;
    }

    static int i = 1;


    public static void main(String... ar) {
        System.out.println(CInitTest.i);
    }
}
