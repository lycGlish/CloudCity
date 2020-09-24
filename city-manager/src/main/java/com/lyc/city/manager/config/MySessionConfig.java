package com.lyc.city.manager.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author lyc
 * @date 2020/9/22 16:21
 */
@Configuration
public class MySessionConfig {

    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("CITYSESSION");
//        defaultCookieSerializer.setDomainName(".gulimail.com"); //将JSESSIONID作用域提高到父域名
        return defaultCookieSerializer;
    }

    //配置序列化类型为JSON
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return new GenericFastJsonRedisSerializer();
    }
}