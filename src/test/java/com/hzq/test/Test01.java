package com.hzq.test;

import com.google.common.collect.Lists;
import com.hzq.entity.User;

import java.util.ArrayList;

/**
 * Created by hzq on 16/7/22.
 */
public class Test01 {


    public static void main(String[] args) {
//
//        ArrayList<Object> objects = Lists.newArrayList(1);
//        for (Object o : objects) {
//            objects.remove(o);
//        }
        ArrayList<Integer> objects = Lists.newArrayList(1);

        for (int i = 0; i < objects.size(); i++) {
            objects.remove(objects.get(i));
        }
//        for (Integer o : objects) {
//            objects.remove(o);
//        }

    }
}
