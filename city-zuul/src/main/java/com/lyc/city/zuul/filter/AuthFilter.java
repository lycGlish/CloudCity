package com.lyc.city.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyc.city.entity.MemberEntity;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lyc
 * @date 2020/10/17 17:24
 */
@Component
public class AuthFilter extends ZuulFilter {

    private boolean compareBlockList(String url) {
        switch (url) {
            case "/city-manager/":
            case "/city-manager/dangerInfo":
            case "/city-manager/recordInfo":
            case "/city-manager/hideInfo":
            case "/city-manager/member":
            case "/city-manager/systemMenu":
            case "/city-manager/camera":
            case "/city-manager/memberInfo":
                return true;
            default:
                return false;
        }
    }

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
            // 获取请求中的路径和用户信息
            Object memberObject = request.getSession().getAttribute("member");
            String url = ctx.getRequest().getRequestURI();

            // 判断路径是否要拦截
            if (compareBlockList(url)) {
                // 路径在拦截名单中
                if (memberObject == null) {
                    // session中没有登录信息，跳转至登录页面
                    ctx.getResponse().sendRedirect("/city-user/login");
                } else {
                    // session信息转成MemberEntity
                    MemberEntity member = JSONObject.parseObject(JSON.toJSONString(memberObject), MemberEntity.class);
                    if (member.getMemberLevel() == 0) {
                        // 用户权限为普通用户时均跳转至首页
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
