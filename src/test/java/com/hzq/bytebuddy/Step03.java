package com.hzq.bytebuddy;

import com.hzq.bytebuddy.entitypack.DefaultMethod;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.DefaultCall;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * Step03
 * Created by hzq on 16/11/14.
 */
public class Step03 {
    private static final String PATH = "/Users/hzq/Desktop";


    @Test
    /**
     * access to the super implementations of a dynamic type's methods.
     */
    public void test01() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        //@SuperCall方法 如果没有返回值 参数可以是一个Runnable类型的
        DynamicType.Unloaded<MemoryDatabase> unloaded = new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .name("com.hzq.bytebuddy.yyy9")
                .method(named("load")).intercept(MethodDelegation.to(LoggerInterceptor.class)) //被代理方法 overridable methods
                .make();
        unloaded.saveIn(new File(PATH));
        //interceptor增强,然后进行输出
        unloaded.load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .load("start");

        System.out.println("====================================================");

        DynamicType.Unloaded<MemoryDatabase> unloaded2 = new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .name("com.hzq.bytebuddy.yyy10")
                .method(named("load")).intercept(MethodDelegation.to(ChangingLoggerInterceptor.class))
                .make();
        unloaded2.saveIn(new File(PATH));

        unloaded2.load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .load("start");
    }

    @Test
    /**
     * @RuntimeType注解 to provide a single interception method for both source methods
     */
    public void test02() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        DynamicType.Unloaded<Loop> unloaded = new ByteBuddy()
                .subclass(Loop.class)
                .name("com.hzq.bytebuddy.yyy11")
                .method(named("loop")).intercept(MethodDelegation.to(Interceptor.class)) //被代理方法 overridable methods
                .make();
        unloaded.saveIn(new File(PATH));

        Loop newInstance = unloaded.load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(newInstance.loop(1));
        System.out.println(newInstance.loop("1"));

    }


    @Test
    /**
     * @DefaultCall,@Defalut 注解 代理default方法  (类似于@SuperCall 和 @Super)
     */
    public void test03() throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        DynamicType.Unloaded<Loop> unloaded = new ByteBuddy()
                .subclass(Loop.class)
                .implement(DefaultMethod.class)
                .intercept(MethodDelegation.to(RunnableClass.class))
                .make();
        unloaded.saveIn(new File(PATH));

        DynamicType.Loaded<Loop> loaded = unloaded.load(getClass().getClassLoader());

        Object instance = loaded.getLoaded().getDeclaredConstructor().newInstance();
        Method method = loaded.getLoaded().getMethod("caca");
        method.invoke(instance);
        System.out.println("=======================================");

        DynamicType.Unloaded<Loop> unloaded1 = new ByteBuddy()
                .subclass(Loop.class)
                .implement(DefaultMethod.class)
                .intercept(MethodDelegation.to(DefaultCallSuper.class))
                .make();
        unloaded.saveIn(new File(PATH));
        DynamicType.Loaded<Loop> loaded1 = unloaded1.load(getClass().getClassLoader());
        Object instance1 = loaded1.getLoaded().getDeclaredConstructor().newInstance();
        Method method1 = loaded1.getLoaded().getMethod("caca");
        method1.invoke(instance1);
    }


    @Test
    /**
     *  @Pipe 注解   you would normally register an interceptor on the instance level instead of registering a static interceptor on the class level.
     *              注册了一个 实例级别的interceptor 而不是 注册一个class的interceptor
     *
     */
    public void test04() throws IllegalAccessException, InstantiationException, IOException {
        DynamicType.Unloaded<MemoryDatabase> unloaded = new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .name("com.hzq.bytebuddy.yyy12")
                .method(named("load")).intercept(MethodDelegation
                        .to(new ForwardingLoggerInterceptor(new MemoryDatabase()))  //参数由.class -> object
                        .appendParameterBinder(Pipe.Binder.install(Function.class)))
                .make();
        unloaded.saveIn(new File(PATH));

        MemoryDatabase newInstance = unloaded.load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();
        newInstance.load("abc");
    }


}
