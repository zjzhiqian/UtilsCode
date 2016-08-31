package com.hzq.project.spi.impl;

import com.hzq.project.spi.HelloWord;

/**
 * HelloWordHisImpl
 * Created by hzq on 16/8/31.
 */
public class HelloWordHisImpl implements HelloWord {
    @Override
    public void sayHello() {
        System.out.println("HisImpl");
    }
}
