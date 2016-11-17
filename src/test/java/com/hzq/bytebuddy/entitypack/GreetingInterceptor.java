package com.hzq.bytebuddy.entitypack;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

/**
 * Created by hzq on 16/11/11.
 */
public class GreetingInterceptor {
    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments,
                            @Origin Method method) {
        // intercept any method of any signature
        System.out.println("hello from  " + method.getName());
        return method;
    }
}

