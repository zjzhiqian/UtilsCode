package com.hzq.project.spi;

import java.util.ServiceLoader;

/**
 * Created by hzq on 16/8/31.
 */
public class Main {
    public static void main(String[] args) {
        ServiceLoader<HelloWord> load = ServiceLoader.load(HelloWord.class);
        for (HelloWord loa:load){
            loa.sayHello();
        }
//        System.out.println(load.);
//        load.forEach(HelloWord::sayHello);
    }
}
