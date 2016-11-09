package com.hzq.project.test.spring;

import com.hzq.project.common.redis.RedisLock;
import com.hzq.project.test.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.StandardEnvironment;

import java.util.Date;

/**
 * Created by hzq on 16/10/31.
 */
@Configuration
@Order(1)
@Import({User.class})
@PropertySource("classpath:mysql.properties")
public class ConfigTest implements TtDefault{

    @Value("${myname}")
    private String myname;

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    @Bean
    Date date() {
        return new Date();
    }

    public static void main(String[] args) {

        ConfigurationClassPostProcessor postProcessor = new ConfigurationClassPostProcessor();
        postProcessor.setEnvironment(new StandardEnvironment());
//        SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("test", new RootBeanDefinition(ConfigTest.class));
//        registry.registerBeanDefinition("HzqConfig2", new RootBeanDefinition(HzqConfig2.class));

        //处理BeanDefinitionRegistry
        postProcessor.postProcessBeanDefinitionRegistry(factory);

        postProcessor.postProcessBeanFactory(factory);

        //输出结果
        System.out.println(factory.getBeanDefinition("test"));//可以输出
        System.out.println(factory.getBeanDefinition("date"));//可以输出
        System.out.println(factory.getBeanDefinition("user"));//可以输出
    }
}
