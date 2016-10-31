package com.hzq.project.test.spring;

import com.hzq.project.test.entity.User;
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
@Import({User.class})
public class ConfigTest {

    @Bean
    Date date() {
        return new Date();
    }

    public static void main(String[] args) {
        ConfigurationClassPostProcessor postProcessor = new ConfigurationClassPostProcessor();
        SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        registry.registerBeanDefinition("test", new RootBeanDefinition(ConfigTest.class));

        //处理BeanDefinitionRegistry
        postProcessor.postProcessBeanDefinitionRegistry(registry);
        //输出结果
        System.out.println(registry.getBeanDefinition("test"));//可以输出
        System.out.println(registry.getBeanDefinition("date"));//可以输出
        System.out.println(registry.getBeanDefinition("user"));//可以输出
    }
}
