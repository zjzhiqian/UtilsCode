package com.hzq.jvm;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * Created by hzq on 16/6/30.
 */
public class DynamicProxyTest {
    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    static class DynamicProxy implements InvocationHandler {
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

    @Test
    public void generateDynamicClass(){
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
