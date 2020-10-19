package com.lyc.city.gateway.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

/**
 * @author lyc
 * @date 2020/10/9 17:12
 */
@Component
public class MyGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        RequestPath path = exchange.getRequest().getPath();

        return exchange.getSession().flatMap(session -> {
            //重定向(redirect)到登录页面
            if(path.toString().equals("/city-manager/")&&session.getAttribute("member")==null){
                ServerHttpResponse response = exchange.getResponse();
                //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set(HttpHeaders.LOCATION, "http://localhost:88/city-user/login");
                return response.setComplete();
            }
            // 继续执行
            return chain.filter(exchange);
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
