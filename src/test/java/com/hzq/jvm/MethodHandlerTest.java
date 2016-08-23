package com.hzq.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * invoke包
 * Created by hzq on 16/6/30.
 */
public class MethodHandlerTest {

    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        getPrintlnMH(System.out).invokeExact("system out");
        getPrintlnMH(new ClassA()).invokeExact("classA");
    }


    /**\
     * 返回一个 MethodHandle
     * @param receiver 接收者对象
     * @return 返回MethodHandle对象
     * @throws Throwable
     */
    private static MethodHandle getPrintlnMH(Object receiver) throws Throwable {
        final MethodType methodType = MethodType.methodType(void.class, String.class);  //返回类型,参数类型

        return lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver); //方法名
    }

}
