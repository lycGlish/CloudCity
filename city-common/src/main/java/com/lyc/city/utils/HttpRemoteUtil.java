package com.lyc.city.utils;

import com.lyc.city.constant.HttpRemoteConstant;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author lyc
 * @date 2020/8/26 14:45
 */
public class HttpRemoteUtil {

    public static String HttpRemotePackage(String request, int code) {

        String SERVICE_URL, url;
        int SUCCESS;

        if (code == HttpRemoteConstant.HttpEnum.HTTP_MAP_COORDINATE.getCode()) {
            SERVICE_URL = "http://api.map.baidu.com/geocoding/v3/?address=";
            SUCCESS = 200;
            url = request + "&output=json&ak=CsItNKwRaLZ8GXXaeyQI6kRozCMrtKcF&callback=showLocation";
            return HttpRemoteRequest(url, SERVICE_URL, SUCCESS);
        } else if (code == HttpRemoteConstant.HttpEnum.HTTP_IMAGE_IDENTIFY.getCode()) {
            SERVICE_URL = "http://120.26.65.52:5000/detect?url=";
            SUCCESS = 201;
            url = request;
            return HttpRemoteRequest(url, SERVICE_URL, SUCCESS);
        } else {
            return null;
        }

    }

    private static String HttpRemoteRequest(String url, String SERVICE_URL, int SUCCESS) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 给访问路径加上传递的参数 url
        String do_url = SERVICE_URL + url;
        // api设定的是做post方法,可以更改为get
        HttpGet httpGet = new HttpGet(do_url);
        // 获取执行后的response
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            // 判断访问连接状态
            if (statusCode == SUCCESS) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
