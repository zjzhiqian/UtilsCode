package com.hzq.spring;

import com.hzq.project.test.spring.ConfigTest;
import com.sun.tools.javac.util.Convert;
import org.junit.Test;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;

import static org.junit.Assert.assertTrue;


/**
 * Created by hzq on 16/11/17.
 */
public class Test01 {


    @Test
    /**
     * 测试 @Profile注解bean
     */
    public void test01() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context-mysql-datasource.xml");
        context.getEnvironment().setActiveProfiles("prod");
        context.refresh();
        assertTrue(context.containsBean("ccc"));
        assertTrue(!context.containsBean("bbb"));
    }

    @Test
    /**
     * 测试 @Scope
     */
    public void test02() throws InterruptedException, IOException {
        MetadataReader metadataReader = new SimpleMetadataReaderFactory().getMetadataReader("com.hzq.project.test.web.AdviceTest");
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(metadataReader);
        ScopeMetadata scopeMetadata = new AnnotationScopeMetadataResolver().resolveScopeMetadata(beanDefinition);
        ScopedProxyMode scopedProxyMode = scopeMetadata.getScopedProxyMode();
        assertTrue(registry.getBeanDefinitionCount() == 0);
        if (!scopedProxyMode.equals(ScopedProxyMode.NO)) {
            boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
            BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, "beanName");
            ScopedProxyUtils.createScopedProxy(holder, registry, proxyTargetClass);
        }
        assertTrue(registry.getBeanDefinitionCount() == 1);
    }

    @Test
    /**
     * 测试 byte[] readClass(final InputStream is, boolean close)
     */
    public void test03() throws InterruptedException, IOException {
        MetadataReader metadataReader = new SimpleMetadataReaderFactory().getMetadataReader("com.hzq.spring.ZK01");
        System.out.println(metadataReader);
    }


    @Test
    /**
     *  测试 ConfigurationClassPostProcessor postProcessBeanDefinitionRegistry
     */
    public void test04() throws InterruptedException, IOException {

        ConfigurationClassPostProcessor postProcessor = new ConfigurationClassPostProcessor();
        postProcessor.setEnvironment(new StandardEnvironment());
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        factory.registerBeanDefinition("test", new RootBeanDefinition(ConfigTest.class));
        //处理BeanDefinitionRegistry
        postProcessor.postProcessBeanDefinitionRegistry(factory);
        postProcessor.postProcessBeanFactory(factory);
        //输出结果
        System.out.println(factory.getBeanDefinition("test"));//可以输出
        System.out.println(factory.getBeanDefinition("date"));//可以输出
        System.out.println(factory.getBeanDefinition("user"));//可以输出
    }





}
