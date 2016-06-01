package com.hzq.lock;


import java.util.stream.Stream;

/**
 * Created by hzq on 16/5/26.
 */

public class FalseSharing implements Runnable {

    private final static int NCPU = Runtime.getRuntime().availableProcessors();
    private final static long TIMES = 500 * 1000 * 1000L;
    private final int arrayIndex;
    private static VolatileLong[] longs1;
    private static VolatileLong2[] longs2;
    private static VolatileLong3[] longs3;

    private static void init() {
        longs1 = new VolatileLong[NCPU];
        longs2 = new VolatileLong2[NCPU];
        longs3 = new VolatileLong3[NCPU];
        for (int i = 0; i < longs1.length; i++) {
            longs1[i] = new VolatileLong();
        }
        for (int i = 0; i < longs2.length; i++) {
            longs2[i] = new VolatileLong2();
        }
        for (int i = 0; i < longs3.length; i++) {
            longs3[i] = new VolatileLong3();
        }
    }

    private FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(String... agrs) throws Exception {
        init();
        long start = System.nanoTime();
        Thread[] threads = new Thread[NCPU];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        Stream.of(threads).forEach(Thread::start);
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    public void run() {
        long i = TIMES + 1;
        while (0 != --i) {
            longs3[arrayIndex].value = i;
        }
    }

    private final static class VolatileLong {
        volatile long value = 0L;
    }

    // long padding避免false sharing
    private final static class VolatileLong2 {
        volatile long p0, p1, p2, p3, p4, p5, p6;
        volatile long value = 0L;
        volatile long q0, q1, q2, q3, q4, q5, q6;
    }

    //-XX:-RestrictContended
    @sun.misc.Contended
    private final static class VolatileLong3 {
        volatile long value = 0L;
    }
}