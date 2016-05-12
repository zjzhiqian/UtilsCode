package com.hzq.annotation;


/**
 * Created by hzq on 16/5/12.
 */
public class AnnoTest {


    public static void main(String... agrs) {
        Attr attr = AnnoTest.class.getAnnotation(Attr.class);
        System.out.println(attr.value());
    }
}
