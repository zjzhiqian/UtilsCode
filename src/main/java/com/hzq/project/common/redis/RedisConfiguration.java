package com.hzq.project.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis Config  File
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfiguration {
    /**
     * redis IP.
     */
    @Value("${redis.ip}")
    private String host;

    /**
     * redis port.
     */
    @Value("${redis.port}")
    private int port;

    /**
     * redis password.
     */
    @Value("${redis.password:}")
    private String password;

    /**
     * redis maxTotal.
     */
    @Value("${redis.maxTotal}")
    private int maxTotal;

    /**
     * redis maxIdle.
     */
    @Value("${redis.maxIdle}")
    private int maxIdle;

    /**
     * redis minIdle.
     */
    @Value("${redis.minIdle}")
    private int minIdle;

    /**
     * redis maxWait.
     */
    @Value("${redis.maxWait}")
    private int maxWait;

    /**
     * redis testOnBorrow.
     */
    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    /**
     * 初始化.
     *
     * @return 返回JedisPoolConfig
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }

    /**
     * 设置范围.
     *
     * @return jedis
     */
    @Bean
    @Scope("prototype")
    @Autowired
    public Jedis jedis(JedisPool jedisPool) {
        return jedisPool.getResource();
    }

    /**
     * 获取JedisPool.
     *
     * @return JedisPool
     */
    @Bean
    public JedisPool jedisPool() {
        if (password == null || "".equals(password)) {
            return new JedisPool(jedisPoolConfig(), host, port, 0);
        } else {
            return new JedisPool(jedisPoolConfig(), host, port, 0, password);
        }

    }
}
