package com.hzq.hystrix;

import com.netflix.hystrix.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by hzq on 17/11/19.
 */
public class HelloWorldCommand extends HystrixCommand<String> {
    private final String name;

    public HelloWorldCommand(String name) {
        //最少配置:指定命令组名(CommandGroup) 在不指定ThreadPoolKey的情况下，字面值用于对不同依赖的线程池/信号区分
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                //每个CommandKey代表一个依赖抽象,相同的依赖要使用相同的CommandKey名称。依赖隔离的根本就是对相同CommandKey的依赖做隔离.
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
                 /* 使用HystrixThreadPoolKey工厂定义线程池名称*/
//                当对同一业务依赖做隔离时使用CommandGroup做区分,但是对同一依赖的不同远程调用如(一个是redis 一个是http),可以使用HystrixThreadPoolKey做隔离区分
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool"))
                /* 配置依赖超时时间,500毫秒*/
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500))
        );
        this.name = name;
    }


    @Override
    protected String getFallback() {
        return "execute failed";
    }

    @Override
    protected String run() throws InterruptedException {
        // 依赖逻辑封装在run()方法中
        TimeUnit.MILLISECONDS.sleep(1000);
        return "Hello " + name + " thread:" + Thread.currentThread().getName();
    }


}
