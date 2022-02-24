package com.kiv.kiveiruapi.xshell.webssh.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSSH的业务逻辑
 */
public interface WebSSHService {
    /**
     * @Description: 初始化连接
     * @Param: WebSocket会话
     * @return: void
     */
    void initConnection(WebSocketSession session);

    /**
     * 处理客户端发送的数据
     * @param buffer 客户端发送过来的数据
     * @param session 会话
     */
    void recvHandle(String buffer, WebSocketSession session);

    /**
     * 发送消息到终端
     * @param session 会话
     * @param buffer 发送内容
     * @throws IOException
     */
    void sendMessage(WebSocketSession session, String type, byte[] buffer) throws IOException;

    /**
     * 关闭会话
     * @param session 会话
     */
    void close(WebSocketSession session);
}
