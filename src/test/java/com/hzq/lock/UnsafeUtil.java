package com.hzq.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by hzq on 16/5/12.
 */
public class UnsafeUtil {

    public static Unsafe getUnfase() {
        Unsafe unsafe ;
        try {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("get unsafe Instance failed");
        }
        return unsafe;
    }

    public static long getFieldOffSet(Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return getUnfase().objectFieldOffset(field);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("unable to get Field");
        }
    }
}
