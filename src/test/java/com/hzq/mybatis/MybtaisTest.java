package com.hzq.mybatis;

import com.hzq.project.common.redis.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hzq on 16/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context-spring.xml","classpath:context-mysql-datasource.xml"})
public class MybtaisTest {

    @Autowired
    RedisLock redisLock;
    @Test
    public void test1(){
        System.out.println(1);
    }
}
