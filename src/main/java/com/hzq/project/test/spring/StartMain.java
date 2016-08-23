package com.hzq.project.test.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 启动spring容器
 * Created by hzq on 16/8/23.
 */
public class StartMain {

    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("classpath:context-*.xml");

    }
}
