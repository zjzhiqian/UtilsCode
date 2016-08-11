package com.hzq.struct.sort;

import org.junit.After;
import org.junit.Test;

/**
 * 各种排序算法
 * Created by hzq on 16/8/4.
 */
public class Sort {

    private static int[] a = {2, 4, 3, 2, 1, 5, 63, 23};


    @After
    public void info() {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i != a.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println();
    }


    @Test
    /**
     * 冒泡排序
     */
    public void test01() {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[i]) {
                    int tmp = a[j];
                    a[j] = a[i];
                    a[i] = tmp;
                }
            }
        }
    }

    @Test
    /**
     * 插入排序 (逆序数, N个互异数的平均逆序数N*(N-1)/2)
     */
    public void test02() {
        int j;
        for (int i = 0; i < a.length; i++) {
            int tmp = a[i];
            for (j = i; j > 0 && tmp < a[j - 1]; j--) {
                a[j] = a[j - 1];
            }
            a[j] = tmp;
        }
    }

}
