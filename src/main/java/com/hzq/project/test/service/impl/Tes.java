package com.hzq.project.test.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hzq on 16/11/30.
 */
@Configuration
public class Tes{

    @Bean
    public static CartServiceImpl tt(){
        return new CartServiceImpl();
    }


    @Bean
    public TestServiceImpl tt2(){
        return new TestServiceImpl();
    }

}
