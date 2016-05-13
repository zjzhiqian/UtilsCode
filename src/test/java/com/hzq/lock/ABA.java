package com.hzq.lock;

import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS存在的ABA问题
 * Created by hzq on 16/5/12.
 */
public class ABA {

    private volatile int target = 10;
    private static long targetOffSet;
    private static Unsafe unsafe;

    static {
        unsafe = UnsafeUtil.getUnfase();
        targetOffSet = UnsafeUtil.getFieldOffSet(ABA.class, "target");
    }

    private static AtomicStampedReference<Integer> atomRef = new AtomicStampedReference<>(10, 0);

    /**
     * ABA存在的问题
     */
    private void showProblem() {
        new Thread(() -> {
            unsafe.compareAndSwapInt(this, targetOffSet, 10, 11);
            System.out.println("targetChanged ->" + target);
            unsafe.compareAndSwapInt(this, targetOffSet, 11, 10);
            System.out.println("targetChanged ->" + target);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = unsafe.compareAndSwapInt(this, targetOffSet, 10, 13);
            System.out.println(result); // true
        }).start();
    }

    /**
     * ABA的解决方案之一
     */
    private void showResolve() {
        new Thread(() -> {
            atomRef.compareAndSet(10, 11, atomRef.getStamp(), atomRef.getStamp() + 1);
            System.out.println("targetChanged ->" + atomRef.getReference());
            atomRef.compareAndSet(11, 10, atomRef.getStamp(), atomRef.getStamp() + 1);
            System.out.println("targetChanged ->" + atomRef.getReference());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int stamp = atomRef.getStamp();
            boolean result = atomRef.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println(result); // false
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        ABA aba = new ABA();
        aba.showProblem();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("===============");
        aba.showResolve();
    }
}