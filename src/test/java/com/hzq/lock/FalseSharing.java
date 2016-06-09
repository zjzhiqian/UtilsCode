package com.hzq.lock;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * Created by hzq on 16/5/26.
 */

public class FalseSharing implements Runnable {

    public final static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;
    private static VolatileLong[] longs1;
    private static VolatileLong2[] longs2;
    private static VolatileLong3[] longs3;

    private static void init() {
        longs1 = new VolatileLong[NUM_THREADS];
        longs2 = new VolatileLong2[NUM_THREADS];
        longs3 = new VolatileLong3[NUM_THREADS];
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
        Thread[] threads = new Thread[NUM_THREADS];
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
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs3[arrayIndex].value = i;
        }
    }

    public final static class VolatileLong {
        public volatile long value = 0L;
    }

    // long padding避免false sharing
    public final static class VolatileLong2 {
        volatile long p0, p1, p2, p3, p4, p5, p6;
        public volatile long value = 0L;
        volatile long q0, q1, q2, q3, q4, q5, q6;
    }

    //-XX:-RestrictContended
    @sun.misc.Contended
    public final static class VolatileLong3 {
        public volatile long value = 0L;
    }
}