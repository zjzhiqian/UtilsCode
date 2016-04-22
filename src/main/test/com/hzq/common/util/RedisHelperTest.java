package com.hzq.common.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * Created by hzq on 16/4/21.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-spring.xml"})
public class RedisHelperTest extends AbstractJUnit4SpringContextTests{


    @Test
    public  void check(){

        Jedis jedis = RedisHelper.getJedis();
        jedis.set("key1","hzq");
        System.out.println("12");
    }


}
