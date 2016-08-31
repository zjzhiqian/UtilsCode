package com.hzq.project.common.beanFactoryProcessors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * MyBeanFactoryPostProcessor
 * Created by hzq on 16/8/22.
 */
public class MyBeanFactoryPostProcessor  implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.err.println("调用MyBeanFactoryPostProcessor的postProcessBeanFactory");
//        BeanDefinition bd = beanFactory.getBeanDefinition("httpUtils");
//
//        System.err.println("httpUtils是不是 singleTon: "+bd.isSingleton());
    }
}
