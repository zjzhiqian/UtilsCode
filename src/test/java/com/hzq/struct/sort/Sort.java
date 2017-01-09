package com.hzq.struct.sort;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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

    }

    //      1  2  3  4  6  7  8  <-----5
    @Test
    public void test02() {
        int j;
        for (int i = 0; i < arr.length; i++) {
            int tmp = arr[i];
            for (j = i; j > 0 && arr[j - 1] > tmp; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

    @Test
    public void test03() {
//        for (int i = 0, j = i + 1; i < arr.length; j = ++i) {
//            int tmp = arr[j];
//            while (tmp < arr[j - 1]) {
//                arr[j] = arr[j - 1];
//                if (j-- == 0) break;
//            }
//            arr[j] = tmp;
//        }


        for (int i = 0, j = i + 1; i < arr.length; j = ++i) {
            int tmp = arr[j];
            while (tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                if (j-- == 0) break;
            }
            arr[j] = tmp;
        }


    }

    private String encrypt(String content, Integer position) {
        int z = 'Z';
        char[] chars = new char[content.length()];
        IntStream.range(0, content.length()).forEach(index -> {
            char c = content.charAt(index);
            int d = c + position <= z ? c + position : c + position - 26;
            chars[index] = (char) d;
        });
        return new String(chars);
    }

    @Test
    public void test04() throws Exception {
        System.out.println(Integer.toBinaryString('A'));
        System.out.println(Integer.toBinaryString('B'));
        System.out.println(encrypt("ABCFE", 4));
    }
}
