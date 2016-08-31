package com.hzq.project.test.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * StartMain
 * Created by hzq on 16/8/23.
 */
public class StartMain {

    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("context-*.xml");

    }
}
