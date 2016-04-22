package com.hzq.common.redis;

import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**w
 * Created by hzq on 16/4/22.
 */
public class CustomTest {
    static final JedisPool jedisPool = new JedisPool("127.0.0.1");
    static volatile boolean flag = false;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0 ;i <1000;i++){
            service.execute(new Task());
        }
    }
    private static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("running..");
            Long result = jedisPool.getResource().setnx("key", "1");
            if (result == 1 && !flag) {
                flag = true;
            } else if (result == 1 && flag) {
                System.out.println("error.....");
                System.exit(3);
            }

        }
    }
}
