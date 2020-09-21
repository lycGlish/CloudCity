package com.lyc.city.constant;

/**
 * @author lyc
 * @date 2020/9/4 11:59
 */
public class FlagConstant {

    public enum InfoEnum {
        INFO_ALL(2, "所有消息"),INFO_UP(1, "展示"), INFO_DOWN(0, "不展示");

        private int code;
        private String msg;

        InfoEnum(int code, String msg) {
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

    public enum TaskEnum {
        TASK_TODO(1, "待做"), TASK_DO(0, "已做");

        private int code;
        private String msg;

        TaskEnum(int code, String msg) {
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
