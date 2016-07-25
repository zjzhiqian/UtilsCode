package com.dbunit.test.dao.impl;

import com.hzq.project.test.dao.TestMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by hzq on 16/7/25.
 */
public class Test001 extends BaseApplicationContext {
    @Autowired
    private TestMapper testMapper;


    @Test
    public void test001(){

        Map map = testMapper.getOne();
        System.out.println("1");

    }
}
