package com.hzq.common.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzq on 15/6/21.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-spring.xml"})
public class RedisPrototypeTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RedisLock lockRedis;


    @Test
    public void checkPrototype() {
        Jedis obj1 = lockRedis.getJedis();
        Jedis obj2 = lockRedis.getJedis();
        Assert.assertTrue(obj1!=obj2);
    }
}