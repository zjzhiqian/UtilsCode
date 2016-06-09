package com.hzq.vvms;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hzq on 16/6/1.
 */
public class StringInternTest {


    @Test
    public void testIntern(){
        //JDK1.7 false,true
//        new String("java22").intern();
        String str2 = new StringBuilder("ja").append("va").toString();
        String str3 = new StringBuilder("jav").append("a22").toString();
        Assert.assertFalse(str2.intern()==str2);
        Assert.assertTrue(str3.intern()==str3);
        //StringBuilder的toString 返回的是在堆中创建的对象
        //java字符串 在启动时已经在常量池中了,返回了它的引用 StringBuilder返回堆中新创建的对象
        //java22字符串 在启动时没在常量池中,str3返回的就是new出来的对象的引用,所以是同一个对象

        String s1 ="aaa";
        String s2= "bbb";

         String s3=s1+s2;
        System.out.println((s1+s2)==s3.intern());

        //JDK1.6中 intern会把首次遇到的字符串复制到永久带中,返回其引用,和堆中new的对象无关

    }
}
