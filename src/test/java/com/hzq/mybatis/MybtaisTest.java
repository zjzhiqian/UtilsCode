package com.hzq.mybatis;

import com.hzq.project.common.redis.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by hzq on 16/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context-spring.xml", "classpath:context-mysql-datasource.xml"})
public class MybtaisTest {

    @Autowired
    RedisLock redisLock;
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    public Object test1() {
        System.out.println(1);
        Map map = new HashMap<>();
        Optional<Map> rs = Optional.ofNullable(sqlSessionTemplate.selectOne("org.mybatis.example.BlogMapper.selectOne"));
        return rs.orElseThrow(() -> new RuntimeException("结果不存在"));
    }
}
