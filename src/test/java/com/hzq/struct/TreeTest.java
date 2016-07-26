package com.hzq.struct;

import java.io.File;

/**
 * Created by hzq on 16/5/3.
 */
public class TreeTest {


    private static void getPaths(String path) {

        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                System.out.println(f.getName());
                if (f.isDirectory()) {
                    getPaths(f.getPath());
                }
            }
        }

    }

    public static void main(String... args) {
        getPaths("/");
    }

}
