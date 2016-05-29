package com.hzq.time;

import com.hzq.lock.UnsafeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by hzq on 16/5/24.
 */
public class TimeTest01 {


    @Test
    public void test01() throws Exception {
       Cell cell = new Cell(3);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ooo = new ObjectOutputStream(baos);
        ooo.writeObject(cell);
        byte[] bys = baos.toByteArray();
        // 长度需要减去 4 个字节：AC ED 00 05
        // AC ED -- 魔法数字
        // 00 05 -- 版本号
        System.out.println(bys.length - 4);

        //56+56+8=120
        //120+8=128

    }

    static final class Cell implements Serializable{
        volatile long p0, p1, p2, p3, p4, p5, p6;
        volatile long value;
        volatile long q0, q1, q2, q3, q4, q5, q6;
        Cell(long x) { value = x; }

        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        static {
            try {
                UNSAFE = UnsafeUtil.getUnfase();
                Class<?> ak = TimeTest01.Cell.class;
                valueOffset = UNSAFE.objectFieldOffset
                        (ak.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

    }

}
