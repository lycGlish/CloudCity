package com.lyc.city.user.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lyc
 * @date 2020/9/14 15:35
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        // 创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.125.128:6379");
        config.useSingleServer().setPassword("lyc1289choose");

        // 创建实例
        return Redisson.create(config);
    }
}
