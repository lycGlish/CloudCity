package com.lyc.city.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author lyc
 * @date 2020/9/14 14:31
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lyc.city.user.feign")
@MapperScan("com.lyc.city.user.dao")
@EnableRedisHttpSession
public class CityUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityUserApplication.class, args);
    }
}
