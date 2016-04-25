package com.hzq.common.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzq on 15/6/21.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-spring.xml"})
public class RedisLockTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RedisLock lockRedis;

    private static ExecutorService service;
    private static Integer tryCount = 0;

    @Test
    public void check() {

        service = Executors.newFixedThreadPool(3);
        while (true) {
            for (int i = 0; i < 5; i++) {
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
            String obj = lockRedis.Handle("key", 300L, arg -> {
                System.out.println();
                return "123";
            });
            System.out.println(obj);
        }
    }


}
