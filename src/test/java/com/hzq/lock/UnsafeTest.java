package com.hzq.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UnsafeTest
 * Created by hzq on 15/7/01.
 */
public class UnsafeTest {
    private  int target = 19;

    public void dodo() throws Exception {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        Field field1 = UnsafeTest.class.getDeclaredField("target");
        Long targetOFFSET = unsafe.objectFieldOffset(field1);
        System.out.println(unsafe.compareAndSwapInt(this, targetOFFSET, 19, 32));
        System.out.println(target);

        //unsafe修改变量的值
        unsafe.putInt(this, targetOFFSET, 3);
        System.out.println(target);
        //...还有其他很多功能,分配内存等等

    }

    public static void main(String... args) throws Exception {
        new UnsafeTest().dodo();
    }
}
