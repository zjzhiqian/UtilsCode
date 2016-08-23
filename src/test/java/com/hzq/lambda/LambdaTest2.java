package com.hzq.lambda;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hzq on 16/5/21.
 */
public class LambdaTest2 {

    @Test
    /**
     * Arrays.sort的线程与调用sort的线程是同一线程吗
     * answer:是的.
     */
    public void test01() {

        List<Integer> list = Arrays.asList(1, 4, 2, 2, 3);
        list.sort((a, b) -> {
            System.out.println(Thread.currentThread().getName());
            return 1;
        });
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    /**
     * 用FileNameFiler进行过滤
     */
    public void  test02(){
        File f = new File("/");
        f.list((file,fileName)->fileName.endsWith(".md"));
    }

    @Test
    /**
     * 用FileNameFiler进行过滤
     */
    public void  test03(){
        File f = new File("/");
        f.list((file,fileName)->fileName.endsWith(".md"));
    }
}
