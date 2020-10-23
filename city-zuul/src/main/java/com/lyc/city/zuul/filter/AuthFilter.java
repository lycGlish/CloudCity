package com.lyc.city.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyc.city.entity.MemberEntity;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lyc
 * @date 2020/10/17 17:24
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(!(authentication instanceof OAuth2Authentication)){
//            return null;
//        }
//        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
//        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
//        // 取出用户身份
//        String principle = userAuthentication.getName();
//        // 取出用户权限
//        List<String> authorities = new ArrayList<>();
//        userAuthentication.getAuthorities().stream()
//                .forEach(c->authorities.add(c.getAuthority()));
//        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
//
//        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
//        Map<String, Object> jsonToken = new HashMap<>(requestParameters);
//        if(userAuthentication!=null){
//            jsonToken.put("principle",principle);
//            jsonToken.put("authorities",authorities);
//        }
//
//        // 身份信息和权限信息存入json，转发给微服务
//        ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));

        try {
            HttpServletRequest request = ctx.getRequest();
            Object memberObject = request.getSession().getAttribute("member");
            String url = ctx.getRequest().getRequestURI();
            if (url.equals("/city-manager/")) {
                if (memberObject == null) {
                    ctx.getResponse().sendRedirect("/city-user/login");
                }else {
                    MemberEntity member = JSONObject.parseObject(JSON.toJSONString(memberObject), MemberEntity.class);
                    if (member.getMemberLevel() == 0) {
                        ctx.getResponse().sendRedirect("/city-info/");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
        return null;
    }
}
