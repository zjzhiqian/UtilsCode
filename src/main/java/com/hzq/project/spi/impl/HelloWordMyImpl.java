package com.hzq.project.spi.impl;

import com.hzq.project.spi.HelloWord;

/**
 * HelloWordMyImpl
 * Created by hzq on 16/8/31.
 */
public class HelloWordMyImpl implements HelloWord {
    @Override
    public void sayHello() {
        System.out.println("MyImpl");
    }
}
