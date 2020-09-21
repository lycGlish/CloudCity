package com.lyc.city.constant;

/**
 * @author lyc
 * @date 2020/8/25 16:58
 */
public class IdentifyConstant {

    public enum IdentifyStatusEnum {
        IMAGE_IDENTIFY_ERROR(0, "识别错误"), IMAGE_IDENTIFY_NORMAL(1, "无积水"),
        IMAGE_IDENTIFY_SMALL(2, "积水"), IMAGE_IDENTIFY_MUCH(3, "内涝"),
        IMAGE_IDENTIFY_SNOW(4, "冰雪");

        private int code;
        private String msg;

        IdentifyStatusEnum(int code, String msg) {
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

    public enum IdentifyEnum {
        IMAGE_IDENTIFY_SYSTEM(1, "系统识别"), IMAGE_IDENTIFY_HUMAN(2, "人工修改");

        private int code;
        private String msg;

        IdentifyEnum(int code, String msg) {
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
