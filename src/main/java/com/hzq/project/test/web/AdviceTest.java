package com.hzq.project.test.web;

import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzq on 16/4/25.
 */
@ControllerAdvice
@Scope(value = "123",proxyMode = ScopedProxyMode.INTERFACES)
public class AdviceTest {


//    @ModelAttribute
//    public void modelAttrubuteMethod() {
//        System.out.println("AdviceTest modelAttrybute1");
//    }


//    @ModelAttribute
//    public void modelAttrubuteMethod2(String id) {
//        System.out.println("AdviceTest modelAttrybute2");
//    }

//    @InitBinder
//    public void InitBinderMethod2() {
//        System.out.println("AdviceTest InitBinder2");
//    }

}
