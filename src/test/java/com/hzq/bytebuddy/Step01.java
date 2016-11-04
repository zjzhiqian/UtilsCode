package com.hzq.bytebuddy;

import jdk.management.resource.ResourceId;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hzq on 16/11/4.
 */
public class Step01 {

    @Test
    public void test01() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();

        assertThat(dynamicType.newInstance().toString(), is("Hello World!"));
    }


    @Test
    public void test02() throws IllegalAccessException, InstantiationException {
        Class<? extends Function> apply = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        System.out.println(apply.newInstance().apply("Byte Buddy"));
    }
}

