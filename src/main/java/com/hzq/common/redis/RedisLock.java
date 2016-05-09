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
     * 设为pubic 如果有需要不用封装,直接用这个方法获取Jedis
     *
     * @return
     */
    public Jedis getJedis() {
        return jedisFactory.getObject();
    }

    /**
     * 进行操作的上锁时长,3s
     */
    private static final Long DEFAULT_LOCK_EXPIRE = 6000L;

    /**
     * 上锁时,尝试获取锁的间隔时间,0.1s
     */
    private static final Long LOCK_POLL_INTERVAL = 100L;

    /**
     * 默认获取锁超时时间,10s
     */
    private final Long LOCK_WAIT_TIMA_OUT = 10000L;

    public <R> R Handle(String key, Function<Jedis, R> callBackFun) {
        return Handle(key, LOCK_WAIT_TIMA_OUT, callBackFun);
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
            jedis.pexpire(Lock.generateLockKey(key), DEFAULT_LOCK_EXPIRE);
            flag = true;
        }
        return flag;
    }

    /**
     * 根据key获得锁执行lockCallBack方法
     *
     * @param key             锁的key
     * @param lockWaitTimeout 最长等待时间,超出等待时间会抛出 LockObtainTimeOutException
     * @param function        调用的方法
     * @return
     */
    public <R> R Handle(String key, Long lockWaitTimeout, Function<Jedis, R> function) {
        Jedis jedis = getJedis();
        Long time1 = System.currentTimeMillis();
        boolean getLock;
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
                    if (System.currentTimeMillis() - time1 < DEFAULT_LOCK_EXPIRE) { //锁没有超时,手动删除    超时了可能会被其他获得
                        jedis.del(Lock.generateLockKey(key));
                    }
                }
            }
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
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


//try {
//        //判断商品是否存在
//        if (itemMapper.selectByPrimaryKey(id) == null) {
//        throw new CartsException("添加失败,商品不存在");
//        }
//        if (!RedisHelper.compareAndSetRequest("CartsService", "addCart", userId.toString(), 10)) {
//        Map map = new HashMap<>();
//        map.put("userId", userId);
//        map.put("id", id);
//        List<CartResult> rs = cartMapper.QueryCarts(map);
//        if (rs.size() > 1) {
//        throw new CartsException("购物车数据出错");
//        }
//        if (rs.size() == 1) {
//        //购物车内有此商品
//        CartResult data = rs.get(0);
//        Integer num = data.getQuantity();
//        if (num + quantity > 0) { //判断更新后购物车数量是否为负数
//        if (quantity > 0) { //判断商品库存是否足够
//        checkStock(id, num, quantity);
//        }
//        flag = cartMapper.updateCartCount(userId, id, quantity);
//        } else {
//        throw new CartsException("购物车商品数量不得少于1");
//        }
//        } else {
//        if (quantity < 1) {
//        throw new CartsException("加入购物车失败,数量必须为正数");
//        }
//        checkStock(id, 0, quantity);
//        Cart cart = new Cart();
//        cart.setItemId(id);
//        cart.setMemberId(userId);
//        cart.setQuantity(quantity);
//
//        Integer storeId = itemMapper.selectStoreIdByItemId(id);
//        if (storeId == null) {
//        throw new CartsException("加入购物车失败,店铺不存在");
//        }
//        cart.setStoreId(storeId);
//        cartMapper.insert(cart);
//        flag = true;
//        }
//        } else {
//        throw new CartsException("点的太快了,稍等下吧");
//        }
//        } finally {
//        RedisHelper2.del(0, "CartsServiceaddCart" + userId);
//        }
//        return flag;
