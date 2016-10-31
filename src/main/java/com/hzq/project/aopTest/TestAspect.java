package com.hzq.project.aopTest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by hzq on 16/9/8.
 */
@Aspect
@Component
public class TestAspect {

    /**
     * 配置切入点,主要为方便同类中其他方法使用此处配置的切入点
     */
    private final String POINT_CUT = "execution(* com.hzq.project.test.service.TestService.*(..))";

    /**
     * 配置前置通知,使用在方法aspect()上注册的切入点
     * 同时接受JoinPoint切入点对象,可以没有该参数
     */
    @Before(POINT_CUT)
    public void beforeTest(){
        System.out.println("before Test");
    }

    /**
     * 配置后置通知,使用在方法aspect()上注册的切入点
     */
    @After(POINT_CUT)
    public void afterTest(){
        System.out.println("after Test");
    }

    /**
     * 配置环绕通知,使用在方法aspect()上注册的切入点
     *
     * @param point JoinPoint的支持接口
     * @return Object
     */
    @Around(POINT_CUT)
    public Object arountTest(ProceedingJoinPoint point){
        System.out.println("before1");
        Object object = null;

        try{
            object = point.proceed();
        }
        catch (Throwable e){
            e.printStackTrace();
        }
        System.out.println("after1");

        return object;
    }
}