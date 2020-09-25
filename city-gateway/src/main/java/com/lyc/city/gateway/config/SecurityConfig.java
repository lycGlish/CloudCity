package com.lyc.city.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;

/**
 * @author lyc
 * @date 2020/9/24 15:15
 */
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, AuthenticationManagerBuilder auth) {
        RedirectServerAuthenticationEntryPoint loginPoint =
                new RedirectServerAuthenticationEntryPoint("/city-user/login");
        http
                .authorizeExchange()
                .pathMatchers("/city-info/**","/city-user/**","/info/static/**","/user/static/**",
                "/manager/static")
                .permitAll()
                // 权限地址
                .pathMatchers("/city-manager/**").hasRole("manager")
                .pathMatchers("/city-manager/**").hasRole("super")
                .and()
                .formLogin().loginPage("/city-user/user/member/doLogin").authenticationEntryPoint(loginPoint)
                .and()
                .logout().logoutUrl("/city-user/logout")
                .and()
                .authorizeExchange().anyExchange().authenticated()
                .and()
                .csrf().disable();


        SecurityWebFilterChain chain = http.build();
        return chain;
    }


}
