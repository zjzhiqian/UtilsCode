package com.hzq.project.test.service.impl;

import com.google.common.collect.ImmutableMap;
import com.hzq.project.test.service.CartService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hzq on 16/11/28.
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private SqlSession sqlsession;


    public void addCount(Integer id, Integer toAdd) {
        sqlsession.update("com.hzq.project.test.dao.TestMapper.upppp", ImmutableMap.of("quantity", toAdd, "id", id));
    }

}
