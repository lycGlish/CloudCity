package com.lyc.city.constant;

/**
 * @author lyc
 * @date 2020/8/26 14:52
 */
public class HttpRemoteConstant {

    public enum HttpEnum {
        HTTP_MAP_COORDINATE(1, "百度地图坐标查询"), HTTP_IMAGE_IDENTIFY(2, "远程图像识别");

        private int code;
        private String msg;

        HttpEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
