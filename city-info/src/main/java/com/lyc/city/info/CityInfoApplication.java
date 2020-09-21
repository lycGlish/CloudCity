package com.lyc.city.info;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lyc
 * @date 2020/8/20 15:20
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lyc.city.info.feign")
@MapperScan("com.lyc.city.info.dao")
public class CityInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityInfoApplication.class, args);
    }
}
