package com.lyc.city.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author lyc
 * @date 2020/8/27 10:15
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.lyc.city.manager.feign")
@MapperScan("com.lyc.city.manager.dao")
@EnableRedisHttpSession
public class CityManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityManagerApplication.class, args);
    }
}
