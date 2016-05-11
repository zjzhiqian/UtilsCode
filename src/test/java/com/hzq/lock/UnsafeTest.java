package com.hzq.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hzq on 16/5/11.
 */
public class UnsafeTest {
    private volatile int target = 19;

    public void dodo() throws Exception {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        Field field1 = UnsafeTest.class.getDeclaredField("target");
        Long targetOFFSET = unsafe.objectFieldOffset(field1);
        //arg1: targetOwnerObject  arg2:tagetOffSet arg3:expectVal arg4:finalVal
        System.out.println(unsafe.compareAndSwapInt(this, targetOFFSET, 19, 32));
        System.out.println(target);

        //unsafe修改变量的值
        unsafe.putInt(this, targetOFFSET, 3);
        System.out.println(target);
        //...还有其他很多功能,分配内存等等


        System.out.println("================");
        AtomicInteger integer = new AtomicInteger(3);
        integer.compareAndSet(3,9);
        System.out.println(integer);
    }

    public static void main(String... args) throws Exception {
        new UnsafeTest().dodo();
    }
}
