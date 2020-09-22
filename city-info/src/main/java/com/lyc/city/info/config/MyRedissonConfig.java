package com.lyc.city.info.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lyc
 * @date 2020/9/11 10:52
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException{
        // 创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.137.130:6379");
        config.useSingleServer().setPassword("lyc1289choose");

        // 创建实例
        return Redisson.create(config);
    }
}
