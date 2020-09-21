package com.lyc.city.constant;

/**
 * @author lyc
 * @date 2020/9/15 11:25
 */
public class MemberConstant {

    public enum MemberStatusEnum {
        MEMBER_STATUS_UNABLE(0, "未启用"), MEMBER_STATUS_ENABLE(1, "已启用"),
        MEMBER_STATUS_BANNED(2, "封禁");

        private int code;
        private String msg;

        MemberStatusEnum(int code, String msg) {
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

    public enum MemberLevelEnum {
        MEMBER_LEVEL_NORMAL(0, "普通用户"), MEMBER_LEVEL_MANAGER(1, "管理员"),
        MEMBER_LEVEL_SUPER(2, "超级管理员");

        private int code;
        private String msg;

        MemberLevelEnum(int code, String msg) {
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
