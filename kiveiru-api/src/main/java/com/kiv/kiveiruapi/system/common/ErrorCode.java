package com.kiv.kiveiruapi.system.common;

public enum ErrorCode {

    PARAMS_ERROR(201, "参数有误"),
    ACCOUNT_PWD_NOT_EXIST(202, "用户名或密码不存在"),
    TOKEN_ERROR(203, "token不合法"),
    ACCOUNT_EXIST(204, "账号已存在"),
    NO_PERMISSION(701, "无访问权限"),
    SESSION_TIME_OUT(901, "会话超时"),
    NO_LOGIN(902, "未登录");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() { return code; }

    public void  setCode(int code) { this.code = code; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }
}
