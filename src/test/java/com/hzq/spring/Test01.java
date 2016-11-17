package com.hzq.spring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;

import static org.junit.Assert.assertTrue;


/**
 * Created by hzq on 16/11/17.
 */
public class Test01 {


    @Test
    public void test01() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context-mysql-datasource.xml");
        context.getEnvironment().setActiveProfiles("prod");
        context.refresh();
        assertTrue(context.containsBean("ccc"));
        assertTrue(!context.containsBean("bbb"));
    }

    @Test
    public void test02() throws InterruptedException, IOException {
        MetadataReader metadataReader = new SimpleMetadataReaderFactory().getMetadataReader("com.hzq.project.test.web.AdviceTest");
        ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(metadataReader);
        ScopeMetadata scopeMetadata = new AnnotationScopeMetadataResolver().resolveScopeMetadata(beanDefinition);
        System.out.println(scopeMetadata.getScopeName());
    }

}
