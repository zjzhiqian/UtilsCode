package com.hzq.test;

/**
 * Created by hzq on 16/6/24.
 */
public class InitClass {

    static {
        System.out.println("initClass init");
    }

    public static final String sta = "3";

    public static final RetailTest t = new RetailTest();



//    public static void main(String... ag) {
//        System.out.println(InitClass.sta);
//    }
}
