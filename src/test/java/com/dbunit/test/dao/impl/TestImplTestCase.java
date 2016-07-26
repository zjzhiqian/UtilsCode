package com.dbunit.test.dao.impl;

import com.dbunit.base.BaseDbUnitTestCase;
import com.google.common.collect.ImmutableList;
import com.hzq.project.test.dao.TestMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by hzq on 16/7/18.
 */
public class TestImplTestCase extends BaseDbUnitTestCase {

    @Autowired
    private TestMapper testMapper;

    @Override
    protected List<String> getXmlName() {
        return ImmutableList.of("/dbunit/test01.xml");
    }

    @Test
    public void test01() {

        Map one = testMapper.getOne();
        System.out.println(1);

    }


    public void test02() {
        Map two = testMapper.getTwo();

    }


}
