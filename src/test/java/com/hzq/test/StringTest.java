package com.hzq.test;

import org.junit.Test;

/**
 * Created by hzq on 16/5/30.
 */
public class StringTest {

    @Test
    public void dod() {

        String kk = "aaabbb";


        String aa = "aaa";
        String bb = aa + "bbb";

        String cc = "bbb";

        System.out.println((aa + cc).intern() == bb.intern());
        System.out.println(aa + cc == bb);


        final String s = "s";
    }
}
