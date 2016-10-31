package com.hzq.project.test.spring;

import com.hzq.project.common.redis.RedisLock;
import org.springframework.context.annotation.Bean;

/**
 * Created by hzq on 16/10/31.
 */
public interface TtDefault {

    @Bean
    default RedisLock getRedisLock(){
        return new RedisLock();
    }
}
