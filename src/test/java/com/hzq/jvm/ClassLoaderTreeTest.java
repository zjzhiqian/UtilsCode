package com.hzq.jvm;

import org.junit.Test;

/**
 * ClassLoaderTreeTest
 * Created by hzq on 16/7/1.
 */
public class ClassLoaderTreeTest {

    @Test
    public void soutLoaderTree(){
        ClassLoader loader = ClassLoaderTreeTest.class.getClassLoader();
//        Thread.currentThread().getContextClassLoader()
        while (loader != null) {
            System.out.println(loader.toString());
            loader = loader.getParent();
        }

        Integer n1 = new Integer(47);
        Integer n2 = 47;
        System.out.println(n1 == n2);
    }


}
