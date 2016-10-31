package com.hzq.project.test.dao.impl;

import com.hzq.project.test.dao.TestMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * testMapper
 * Created by hzq on 16/5/17.
 */
@Repository
public class TestMapperImpl implements TestMapper {
    @Autowired
    private SqlSession sqlsession;

    @Override
    public Map getOne() {
        String name = this.getClass().getInterfaces()[0].getName();
        return sqlsession.selectOne(name + ".getOne");
    }

    @Override
    public Map getTwo() {
        String name = this.getClass().getInterfaces()[0].getName();
        return sqlsession.selectOne(name + ".getTwo");
    }


}
