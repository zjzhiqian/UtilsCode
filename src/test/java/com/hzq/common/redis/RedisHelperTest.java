package com.hzq.common.redis;

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
@ContextConfiguration(locations = {"classpath:applicationContext-spring.xml"})
public class RedisHelperTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void check() {
        Jedis jedis = RedisHelper.getJedis();
        jedis.set("key1", "hzq");
        System.out.println("12");
    }


}










//    @Override
//    public <T> T lockDo(String key, long timeoutMilliseconds, LockCallBack<T> lockCallBack) {
//        try {
//            if(!this.isLocked(key)){
//                return lockCallBack.invoke();
//            }else{
//                Timeout timeout = new Timeout(timeoutMilliseconds);
//                while(!timeout.isTimeout()){
//                    if(!this.isLocked(key)){
//                        return lockCallBack.invoke();
//                    }
//                }
//            }
//            return null;
//        } finally {
//            this.del(Lock.generateLockKey(key));
//        }
//    }
//
//    /**
//     * true:被锁
//     * false:没有被锁
//     */
//    @Override
//    public Boolean isLocked(String key) {
//        long result = super.setnx(Lock.generateLockKey(key), Lock.generateRandomValue(key));
//        //不存在锁
//        if(result == 1){
//            super.pexpire(Lock.generateLockKey(key), Lock.DEFAULT_LOCK_EXPIRE_MILLISENCONDS);
//            return false;
//        }else{
//            return true;
//        }
//    }
