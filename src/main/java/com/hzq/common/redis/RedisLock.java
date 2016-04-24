package com.hzq.common.redis;

import com.hzq.common.exception.LockObtainTimeOutException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.function.Function;

/**
 * 用redis作集群锁,所有web服务器共享一个锁,获取锁失败时,每隔final Long LOCK_POLL_INTERVAL尝试获取锁,每个key加锁时间为DEFAULT_LOCK_EXPIRE_MILLISENCONDS
 * Created by hzq on 16/4/22.
 */

@Component
public class RedisLock {

    /**
     * setJedisFactory  to make it as prototype
     */
    private static ObjectFactory<Jedis> jedisFactory;

    @Autowired
    private void setJedisFactory(ObjectFactory<Jedis> jedisFactory) {
        this.jedisFactory = jedisFactory;
    }

    /**
     * 设为pubic 如果有需要不用封装的话
     * @return
     */
    public Jedis getJedis() {
        return jedisFactory.getObject();
    }

    /**
     * 进行操作的上锁时长,3s
     */
    private static final Long DEFAULT_LOCK_EXPIRE_MILLISENCONDS = 6000L;

    /**
     * 上锁时,尝试获取锁的间隔时间,0.1s
     */
    private static final Long LOCK_POLL_INTERVAL = 100L;

    /**
     * 默认获取锁超时时间,10s
     */
    private final Long LOCK_WAIT_TIMA_OUT = 10000L;

    public <R> R Handle(String key, Function<Jedis,R> callBackFun) {
        return Handle(key, LOCK_WAIT_TIMA_OUT, callBackFun);
    }

    /**
     * 根据key获得锁执行lockCallBack方法
     *
     * @param key             锁的key
     * @param lockWaitTimeout 最长等待时间,超出等待时间会抛出 LockObtainTimeOutException
     * @param function        调用的方法
     * @return
     */
    public <R> R Handle(String key, Long lockWaitTimeout, Function<Jedis,R> function) {
        Jedis jedis = getJedis();
        boolean getLock ;
        try {
            long maxSleepCount = lockWaitTimeout / LOCK_POLL_INTERVAL;
            long sleepCount = 0;
            while (!(getLock = getLock(key, jedis))) {
                if (sleepCount++ >= maxSleepCount) {
                    throw new LockObtainTimeOutException("obtain lock time out for key " + key);
                }
                try {
                    Thread.sleep(LOCK_POLL_INTERVAL);
                } catch (InterruptedException ie) {
                    throw new RuntimeException();
                }
            }
            if (getLock) {
                try {
                    return function.apply(jedis);
                } finally {
                    jedis.del(Lock.generateLockKey(key));
                }
            }
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 尝试获取锁,成功返回true,失败返回false
     *
     * @param key 判断重复的key
     * @return
     * @Param jedis
     */
    private static Boolean getLock(String key, Jedis jedis) {
        long result = jedis.setnx(Lock.generateLockKey(key), Lock.generateRandomValue(key));
        //不存在锁
        boolean flag = false;
        if (result == 1) {
            jedis.pexpire(Lock.generateLockKey(key), DEFAULT_LOCK_EXPIRE_MILLISENCONDS);
            flag = true;
        }
        return flag;
    }

    private static class Lock {
        static String DEFAULT_SUFFIX = "lk";
        static String DEFAULT_DELIMITER = "_";

        public static String generateRandomValue(String key) {
            return String.valueOf(key.hashCode());
        }

        public static String generateLockKey(String key) {
            return key + DEFAULT_DELIMITER + DEFAULT_SUFFIX;
        }

    }

}
