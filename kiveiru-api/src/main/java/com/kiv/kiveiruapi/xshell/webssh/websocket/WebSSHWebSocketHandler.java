package com.kiv.kiveiruapi.xshell.webssh.websocket;

import com.kiv.kiveiruapi.xshell.webssh.constant.ConstantPool;
import com.kiv.kiveiruapi.xshell.webssh.service.WebSSHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


/**
 * WebSocket
 */
@Component
public class WebSSHWebSocketHandler implements WebSocketHandler{
    @Autowired
    private WebSSHService webSSHService;
    private Logger logger = LoggerFactory.getLogger(WebSSHWebSocketHandler.class);

    /**
     * 有用户连接上时的处理
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        logger.info("用户:{},连接WebSSH", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY));
        //调用初始化连接
        webSSHService.initConnection(webSocketSession);
    }

    /**
     * 收到消息的回调 收到消息
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        if (webSocketMessage instanceof TextMessage) {
            logger.info("用户:{},发送命令:{}", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY), webSocketMessage.getPayload());
            //调用service接收消息
            webSSHService.recvHandle(((TextMessage) webSocketMessage).getPayload(), webSocketSession);
        } else if (webSocketMessage instanceof BinaryMessage) {
            logger.info("用户:{},发送二进制消息",webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY));
        } else if (webSocketMessage instanceof PongMessage) {
            logger.info("用户:{},发送pong消息",webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY));
        } else {
            System.out.println("Unexpected WebSocket message type: " + webSocketMessage);
        }
    }

    /**
     * 连接出现错误
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        logger.error("数据传输错误");
    }

    /**
     * 断开连接
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        logger.info("用户:{}断开webssh连接", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY));
        //调用service关闭连接
        webSSHService.close(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
