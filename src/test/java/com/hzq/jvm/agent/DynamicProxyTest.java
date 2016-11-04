package com.hzq.jvm.agent;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import sun.jvm.hotspot.HelloWorld;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hzq on 16/6/30.
 */
public class DynamicProxyTest {

    /**
     * JDK代理
     */
    private class DynamicProxy implements InvocationHandler {
        Object originObj;

        public Object bind(Object originObj) {
            this.originObj = originObj;
            return Proxy.newProxyInstance(originObj.getClass().getClassLoader(), originObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("...");
            return method.invoke(originObj, args);
        }
    }


    /**
     * CGLib代理
     */
    private class DynamicProxyCGLib implements MethodInterceptor {
        private Object originObj;  //委托类

        Object getInstance(Object obj) {
            this.originObj = obj;
            Enhancer enhancer = new Enhancer();  //增强类
            //不同于JDK的动态代理，它不能在创建代理时传obj对 象，obj对象必须被CGLIB包来创建
            enhancer.setSuperclass(this.originObj.getClass()); //设置被代理类字节码（obj将被代理类设置成父类；作为产生的代理的父类传进来的），CGLIB根据字节码生成被代理类的子类
            enhancer.setCallback(this); //this方法拦截
            return enhancer.create(); //创建代理类
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args,
                                MethodProxy methodProxy) throws Throwable {
            System.out.println("obj:" + obj.getClass().getName());  //由CGLib动态生成的代理类实例
            System.out.println("method:" + method.getName());   //上文中实体类所调用的被代理的方法引用
            System.out.println("methodProxy:" + methodProxy.getClass().getName()); //生成的代理类对方法的代理引用
            System.out.println("args:" + args); //参数值列表
            Object object = methodProxy.invokeSuper(obj, args);  //调用代理类实例上的methodProxy方法的父类方法（即实体类RealSubject中的对应方法）
            return object;
        }
    }


    @Test
    /**
     * CGLib代理部分
     * Enhancer enhancer = new Enhancer();  //增强类
     * enhancer.setSuperclass(this.originObj.getClass()); //设置被代理类字节码（obj将被代理类设置成父类；作为产生的代理的父类传进来的),CGLIB根据字节码生成被代理类的子类(所以被代理类不能是final的,非静态方法不能是final,private的)
     * enhancer.setCallback(this); //this方法拦截
     * return enhancer.create(); //创建代理类
     */
    public void test01() {
        Hello hello = (Hello) new DynamicProxyCGLib().getInstance(new Hello());
        hello.sayHello();
    }

    @Test
    /**
     * JDK动态代理部分
     * Proxy.newProxyInstance(originObj.getClass().getClassLoader(), originObj.getClass().getInterfaces(), this);
     *  1.类加载器 2.Class 3.InvocationHandler 处理方法的Handler
     *  实际上生成了一个代理类 存有类变量method,InvocationHandler.invoke()方法
     */
    public void generateDynamicClass() {
        final IHello bind = (IHello) new DynamicProxy().bind(new Hello());
        bind.sayHello();
        byte[] classFile = ProxyGenerator.generateProxyClass(bind.getClass().toString(), Hello.class.getInterfaces());
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("/Users/hzq/Documents/UtilsCode/src/test/java/com/hzq/jvm/a.class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

interface IHello {
    void sayHello();
}

class Hello implements IHello {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
