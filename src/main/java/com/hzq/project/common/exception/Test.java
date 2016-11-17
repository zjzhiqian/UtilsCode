package com.hzq.project.common.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by hzq on 16/11/17.
 */
@Configuration
public class Test {

    @Bean
    @Profile("dev")//Profile为dev时实例化devDemoBean
    public String bbb() {
        return "123";
    }

    @Bean
    @Profile("prod")//Profile为prod时实例化prodDemoBean
    public String ccc() {
        return "233";
    }
}
