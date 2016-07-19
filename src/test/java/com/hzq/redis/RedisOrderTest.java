package com.hzq.redis;

import com.hzq.project.common.redis.RedisHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * BaseTest
 * Created by hzq on 16/6/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context-spring.xml", "classpath:context-mysql-datasource.xml"})
public class RedisOrderTest {

    //article:id    title,user,link,time,votes
    private static final String ART_KEY = "article:";
    private static final String ART_TIME = "article-time:";
    private static final String ART_SCORE = "article-score:";

    private static final String ART_VOTED = "article-voted:";

    @Autowired
    RedisHelper redisHelper;

    @Test
    /**
     * 添加的方法
     */
    public void testInsert() {
        Post post = new Post("user2", "titl2", "www.baidu.com", System.currentTimeMillis(), 2);
        final Long art = RedisHelper.incr(ART_KEY);
        String redisKey = ART_KEY + art;
        final Long title = RedisHelper.hset(redisKey, "title", post.getTitle());
        final Long user = RedisHelper.hset(redisKey, "user", post.getUser());
        final Long link = RedisHelper.hset(redisKey, "link", post.getLink());
        final boolean time = RedisHelper.hset(redisKey, "time", post.getTime());
        final boolean votes = RedisHelper.hset(redisKey, "votes", post.getVotes());
        try (Jedis jedis = RedisHelper.getJedis()) {
            jedis.zadd(ART_TIME, post.getTime().doubleValue(), redisKey);//时间排序因子
            jedis.zadd(ART_SCORE, post.getVotes().doubleValue(), redisKey);//评分排序因子
            jedis.sadd(ART_VOTED + art, post.getUser());
        }
        System.out.println("添加文章成功,key:" + redisKey);
    }

    @Test
    /**
     * 查询方法
     */
    public void testSearch() {
        Integer page = 1;
        Integer pageNum = 20;
        String order = ART_SCORE;
//        String order = ART_TIME;
        Integer start = (page - 1) * pageNum;
        Integer end = start + pageNum - 1;
        try (Jedis jedis = RedisHelper.getJedis()) {
            final Set<String> zrevrange = jedis.zrevrange(order, start, end);
            final List<Post> collect = zrevrange.stream().map(key -> {
                final Map<String, String> postData = jedis.hgetAll(key);
                Post post = new Post();
                try {
                    BeanUtils.populate(post, postData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return post;
            }).collect(toList());
            collect.forEach(System.out::println);
        }
    }

    @Test
    /**
     * 投票方法
     */
    public void testVote() {
        String articleId = "1";
        String user = "user2";

        try (Jedis jedis = RedisHelper.getJedis()) {
            final Long sadd = jedis.sadd(ART_VOTED + articleId, user);
            if (sadd == 1) {
                System.out.println("投票成功");
                jedis.hincrBy(ART_KEY + articleId + "votes", user, 1);//投票数+1
                jedis.zincrby(ART_SCORE, 1d, ART_KEY + articleId);
            } else if (sadd == 0) {
                System.out.println("您已投票");
            }
        }


    }

}
