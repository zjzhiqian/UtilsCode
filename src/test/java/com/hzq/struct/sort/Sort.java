package com.hzq.struct.sort;

import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * 各种排序算法
 * Created by hzq on 16/8/4.
 */
public class Sort {

    private static int[] arr = {1, 23, 7, 3, 2, 435, 543, 2312, 321, 321, 32, 4, 5, 6, 8, 31, 224, 42};


    @After
    public void info() {
        StringJoiner joiner = new StringJoiner(",");
        Arrays.stream(arr).forEach(data -> joiner.add(data + ""));
        System.out.println(joiner);
    }

    @Test
    /**
     * 冒泡排序
     */
    public void test01() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                }
            }
        }
    }

    //      1  2  3  4  6  7  8  <-----5
    @Test
    /**
     * 插入排序 (逆序数, N个互异数的平均逆序数N*(N-1)/2)
     */
    public void test02() {
        for (int i = 0; i < arr.length; i++) {
            int j;
            int tmp = arr[i];
            for (j = i; j > 0 && arr[j - 1] > tmp; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

    @Test
    /**
     * 插入排序另一种形式
     */
    public void test03() {
        int j = 0;
        for (int i = 0; i < arr.length - 1; j = ++i) {
            int tmp = arr[i + 1];
            while (tmp < arr[j]) {
                arr[j + 1] = arr[j];
                if (j-- == 0) {
                    break;
                }
            }
            arr[j + 1] = tmp;
        }


    }

}
