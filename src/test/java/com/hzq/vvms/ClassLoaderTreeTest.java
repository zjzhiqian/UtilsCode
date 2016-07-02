package com.hzq.vvms;

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
    }


}
