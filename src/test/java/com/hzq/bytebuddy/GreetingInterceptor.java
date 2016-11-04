package com.hzq.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

/**
 * Created by hzq on 16/11/4.
 */
public class GreetingInterceptor {
    @RuntimeType
    public Object greet(@AllArguments Object[] allArguments,
                        @Origin Method method) {
        return "Hello from " + method.getName();
    }
}
