package com.lyc.city.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lyc
 * @date 2020/8/20 17:11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CityThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityThirdPartyApplication.class, args);
    }
}
