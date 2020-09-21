package com.lyc.city.constant;

/**
 * @author lyc
 * @date 2020/8/25 16:55
 */
public class SourceConstant {

    public enum ImageEnum {
        IMAGE_SOURCE_USER(1, "用户"), IMAGE_SOURCE_CAMERA(2, "摄像头");

        private int code;
        private String msg;

        ImageEnum(int code, String msg) {
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
