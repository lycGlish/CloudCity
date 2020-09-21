package com.lyc.city.info;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author lyc
 * @date 2020/9/10 17:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CityInfoTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void redission(){
        System.out.println(redissonClient);
    }

    @Test
    public void testStringRedisTemplate(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        // 保存
        ops.set("hello","world_"+ UUID.randomUUID().toString());

        // 查询
        String hello = ops.get("hello");

        System.out.println(hello);
    }
}
