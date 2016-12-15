package com.hzq.base.io;

import java.util.HashMap;

/**
 * Created by hzq on 16/5/17.
 */
public class FileTest {

    public static void main(String[] agrs) {



        HashMap<String, Object> map = new HashMap<>();
        map.put("123", 3);
        map.putIfAbsent("123", 4);
        System.out.println(map);
    }
}
