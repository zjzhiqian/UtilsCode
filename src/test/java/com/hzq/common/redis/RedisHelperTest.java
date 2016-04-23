package com.hzq.common.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by hzq on 15/6/21.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-spring.xml"})
public class RedisHelperTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RedisLock lockRedis;

    private static ExecutorService service;
    private static Integer tryCount = 0;

    @Test
    public void check() {

        service = Executors.newFixedThreadPool(3);
        while (true) {
            for (int i = 0; i < 2; i++) {
                service.execute(new Task());
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
        service = Executors.newFixedThreadPool(3);
        System.out.println("tryCount: " + ++tryCount);
    }


    private class Task implements Runnable {
        @Override
        public void run() {
//            Object obj = lockRedis.Handle("key", 300L, new CallBackFun<Object>() {
//                @Override
//                public Object invoke() {
//                    try {
//                        TimeUnit.SECONDS.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return "123";
//                }
//            });
            String obj = lockRedis.Handle("key", 300L, new Function<Jedis, String>() {
                @Override
                public String apply(Jedis o) {
                    return "123";
                }
            });
            System.out.println(obj);
        }
    }


}
