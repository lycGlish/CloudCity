package com.lyc.city.camera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lyc
 * @date 2020/8/27 10:15
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.lyc.city.camera.feign")
@MapperScan("com.lyc.city.camera.dao")
public class CityCameraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityCameraApplication.class, args);
    }
}
