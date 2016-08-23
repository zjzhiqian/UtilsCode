package com.hzq.project.common.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试Redis setnx方法 是线程安全的
 * Created by hzq on 15/6/22.
 */
public class setnxAtomTest {
    private static final String TEST_KEY = "testKey";
    private static JedisPool jedisPool;
    private static ExecutorService service;
    private static Integer tryCount = 0;
    private static volatile boolean flag = false;

    @Before
    public void init() {
        jedisPool = new JedisPool("115.28.232.136");
    }

    @Test
    public void testAtomSetnx() {
        service = Executors.newFixedThreadPool(3);
        while (true) {
            for (int i = 0; i < 2; i++) {
                service.execute(new setnxAtomTest.Task());
            }
            service.shutdown();
            while (!service.isTerminated()) {

            }
            reInit();
        }
    }

    /**
     * 重新初始化
     */
    public void reInit() {
        Jedis jedis = null;
        try {
            System.out.println("tryCount :" + ++tryCount);
            jedis = jedisPool.getResource();
            jedis.del(TEST_KEY);
            service = Executors.newFixedThreadPool(3);
            flag = false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    private static class Task implements Runnable {
        @Override
        public void run() {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                Long result = jedis.setnx(TEST_KEY, "1");
                if (result == 1 && !flag) {
                    flag = true;
                } else if (result == 1 && flag) {
                    System.out.println("error.....setNx is not an Atom operation ");
                    System.exit(3);
                }
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }
}
