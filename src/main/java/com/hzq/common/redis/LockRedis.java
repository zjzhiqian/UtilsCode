package com.hzq.common.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by hzq on 16/4/22.
 */
public class LockRedis {


    private static Jedis getJedis() {
        return new Jedis("127.0.0.1");
    }


    /**
     * 根据key获得锁执行lockCallBack方法
     *
     * @param key             锁的key
     * @param lockWaitTimeout 超时时间
     * @param lockCallBack    回调函数
     * @param <T>
     * @return
     */
    public <T> T lockDo(String key, long lockWaitTimeout, LockCallBack<T> lockCallBack) {
        try {

            boolean locked = isLocked(key);
            final Long LOCK_POLL_INTERVAL = 1000L;
            long maxSleepCount = lockWaitTimeout / LOCK_POLL_INTERVAL;
            long sleepCount = 0;
            while (!isLocked(key)) {
                if (sleepCount++ >= maxSleepCount) {
                    throw new RuntimeException("obtain lock time out for key " + key);
                }
                try {
                    Thread.sleep(LOCK_POLL_INTERVAL);
                } catch (InterruptedException ie) {
                    throw new RuntimeException();
                }
                locked = isLocked(key);
            }
            if (locked) {
                return lockCallBack.invoke();
            }
            return null;
        } finally {
            getJedis().del(Lock.generateLockKey(key));
        }
    }

    /**
     * 判断是否已经加锁,如果没有加锁返回false
     *
     * @param key
     * @return true 被锁,false 没有被锁
     */
    private static Boolean isLocked(String key) {
        Jedis jedis = getJedis();
        long result = jedis.setnx(Lock.generateLockKey(key), Lock.generateRandomValue(key));
        //不存在锁
        boolean flag = true;
        if (result == 1) {
            jedis.pexpire(Lock.generateLockKey(key), Lock.DEFAULT_LOCK_EXPIRE_MILLISENCONDS);
            flag = false;
        }
        return flag;
    }


    private static class Lock {

        static String DEFAULT_SUFFIX = "lk";

        static String DEFAULT_DELIMITER = "_";

        static Long DEFAULT_LOCK_EXPIRE_MILLISENCONDS = 3000L;


        public static String generateRandomValue(String key) {
            return String.valueOf(key.hashCode());
        }

        public static String generateLockKey(String key) {
            return key + DEFAULT_DELIMITER + DEFAULT_SUFFIX;
        }

    }


}
