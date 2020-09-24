package com.lyc.city.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

/**
 * @author lyc
 * @date 2020/9/24 15:15
 */
//@EnableWebFluxSecurity
public class SecurityConfig {

    //security的鉴权排除的url列表
    private static final String[] excludedAuthPages = {
            "/city-info/"
    };

//    @Bean
//    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        http
//                .authorizeExchange()
//                .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
//                .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
//                .anyExchange().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin() //启动页面表单登陆,spring security 内置了一个登陆页面/login
//                .and().csrf().disable()//必须支持跨域
//                .logout().disable();
//
//        return http.build();
//    }
}
