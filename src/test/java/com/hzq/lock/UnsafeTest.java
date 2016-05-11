package com.hzq.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

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
        System.out.println(unsafe.compareAndSwapInt(this, targetOFFSET, 19, 32));
        System.out.println(target);
    }

    public static void main(String... args) throws Exception {
        UnsafeTest test = new UnsafeTest();
        test.dodo();

    }
}
