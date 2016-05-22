package com.hzq.io;

import java.io.File;

/**
 * Created by hzq on 16/5/17.
 */
public class FileTest {

    public static void main(String[] agrs){
        File f = new File("/Users/hzq/Documents/springMVC06.m2d");
        System.out.println(f.exists());
        System.out.println(f.canExecute());
    }
}
