package com.kiv.kiveiruapi.xshell.webssh.constant;
public class ConstantPool {
    /**
     * 随机生成uuid的key名
     */
    public static final String USER_UUID_KEY = "user_uuid";
    /**
     * 发送指令：连接
     */
    public static final String WEBSSH_OPERATE_CONNECT = "connect";
    /**
     * 发送指令：命令
     */
    public static final String WEBSSH_OPERATE_COMMAND = "command";
    /**
     * 消息类型：连接
     */
    public static final String WEBSSH_MESSAGE_TYPE_CONNECT = "WEBSSH_MESSAGE_TYPE_CONNECT";
    /**
     * 消息类型：命令
     */
    public static final String WEBSSH_MESSAGE_TYPE_COMMAND = "WEBSSH_MESSAGE_TYPE_COMMAND";
    /**
     * 消息类型：异常
     */
    public static final String WEBSSH_MESSAGE_TYPE_ERROR = "WEBSSH_MESSAGE_TYPE_ERROR";
}
