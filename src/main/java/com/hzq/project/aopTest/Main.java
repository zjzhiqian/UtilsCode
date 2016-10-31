package com.hzq.project.aopTest;

import com.hzq.project.test.service.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hzq on 16/9/8.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context-mysql-datasource.xml");
        TestService bean = context.getBean("testServiceImpl", TestService.class);
        bean.aop01();
    }

}
