package com.hzq.project.test.spring;

import com.hzq.project.spi.impl.HelloWordHisImpl;
import com.hzq.project.test.entity.User;
import com.hzq.project.test.service.impl.TestServiceImpl;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.Import;

import java.util.Date;

/**
 * Created by hzq on 16/10/31.
 */
@Configuration
@Import({TestServiceImpl.class})
public class HzqConfig2 {
    @Bean
    HelloWordHisImpl HelloWordHis() {
        return new HelloWordHisImpl();
    }
}
