package com.kiv.kiveiruapi.xshell.webssh.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.*;
import com.kiv.kiveiruapi.xshell.webssh.constant.ConstantPool;
import com.kiv.kiveiruapi.xshell.webssh.dao.WebShellConnRepository;
import com.kiv.kiveiruapi.xshell.webssh.entity.SSHConnectInfo;
import com.kiv.kiveiruapi.xshell.webssh.entity.WebSSHData;
import com.kiv.kiveiruapi.xshell.webssh.entity.WebShellConnEntity;
import com.kiv.kiveiruapi.xshell.webssh.service.WebSSHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* @Description: WebSSH业务逻辑实现
* @Author: NoCortY
* @Date: 2020/3/8
*/
@Service
public class WebSSHServiceImpl implements WebSSHService {
    //存放ssh连接信息的map
    public static Map<String, SSHConnectInfo> sshMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(WebSSHServiceImpl.class);
    //线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private WebShellConnRepository webShellConnRepository;

    /**
     * @Description: 初始化连接
     * @Param: WebSocket会话
     * @return: void
     */
    @Override
    public void initConnection(WebSocketSession session) {
        JSch jSch = new JSch();
        SSHConnectInfo sshConnectInfo = new SSHConnectInfo();
        sshConnectInfo.setjSch(jSch);
        sshConnectInfo.setWebSocketSession(session);
        String uuid = String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY));
        //将这个ssh连接信息放入map中
        sshMap.put(uuid, sshConnectInfo);
    }


    /**
     * 处理客户端发送的数据
     * @param buffer 客户端发送过来的数据
     * @param session 会话
     */
    @Override
    public void recvHandle(String buffer, WebSocketSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 将接收到的数据格式化
        WebSSHData webSSHData = null;
        try {
            webSSHData = objectMapper.readValue(buffer, WebSSHData.class);
        } catch (IOException e) {
            logger.error("Json转换异常");
            logger.error("异常信息:{}", e.getMessage());
            return;
        }
        // 用户ID
        String userId = String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY));
        // 如果是链接命令
        if (ConstantPool.WEBSSH_OPERATE_CONNECT.equals(webSSHData.getOperate())) {
            //找到刚才存储的ssh连接对象
            SSHConnectInfo sshConnectInfo = (SSHConnectInfo) sshMap.get(userId);
            //启动线程异步处理
            WebSSHData finalWebSSHData = webSSHData;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 链接服务器
                        connectToSSH(sshConnectInfo, finalWebSSHData, session);
                    } catch (JSchException | IOException e) {
                        logger.error("webssh连接异常");
                        logger.error("异常信息:{}", e.getMessage());
                        try {
                            // 链接失败，显示到终端
                            sendMessage(session,ConstantPool.WEBSSH_MESSAGE_TYPE_ERROR,e.getMessage().getBytes());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        // 关闭会话
                        close(session);
                    }
                }
            });
            // 终端输入命令
        } else if (ConstantPool.WEBSSH_OPERATE_COMMAND.equals(webSSHData.getOperate())) {
            // 输入的命令
            String command = webSSHData.getCommand();
            // 链接信息
            SSHConnectInfo sshConnectInfo = (SSHConnectInfo) sshMap.get(userId);
            if (sshConnectInfo != null) {
                try {
                    // 发送命令
                    transToSSH(sshConnectInfo.getChannel(), command);
                } catch (IOException e) {
                    logger.error("webssh连接异常");
                    logger.error("异常信息:{}", e.getMessage());
                    try {
                        // 发送失败，返回信息显示到终端
                        sendMessage(session,ConstantPool.WEBSSH_MESSAGE_TYPE_ERROR,e.getMessage().getBytes());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    // 关闭会话
                    close(session);
                }
            }
        } else {
            logger.error("不支持的操作");
        }
    }

    /**
     * 发送消息到终端
     * @param session 会话
     * @param buffer 发送内容
     * @throws IOException
     */
    @Override
    public void sendMessage(WebSocketSession session,String type, byte[] buffer) throws IOException {
        String userId = String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY));
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("type",type);
        map.put("data",new String(buffer));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        session.sendMessage(new TextMessage(json.getBytes()));
    }

    public static void sendMessage(String userId,String type, String message) throws IOException {
        SSHConnectInfo sshConnectInfo = WebSSHServiceImpl.sshMap.get(userId);
        WebSocketSession webSocketSession = sshConnectInfo.getWebSocketSession();
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("type",type);
        map.put("data",message);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        webSocketSession.sendMessage(new TextMessage(json.getBytes()));
    }

    /**
     * 关闭会话
     * @param session 会话
     */
    @Override
    public void close(WebSocketSession session) {
        String userId = String.valueOf(session.getAttributes().get(ConstantPool.USER_UUID_KEY));
        SSHConnectInfo sshConnectInfo = (SSHConnectInfo) sshMap.get(userId);
        if (sshConnectInfo != null) {
            //断开连接
            if (sshConnectInfo.getChannel() != null) sshConnectInfo.getChannel().disconnect();
            //map中移除
            sshMap.remove(userId);
        }
    }

    /**
     * 使用jsch连接终端
     * @param sshConnectInfo 链接信息
     * @param webSSHData 请求数据
     * @param webSocketSession 终端会话
     * @throws JSchException
     * @throws IOException
     */
    private void connectToSSH(SSHConnectInfo sshConnectInfo, WebSSHData webSSHData, WebSocketSession webSocketSession) throws JSchException, IOException {
        Session session = null;
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        //获取jsch的会话
        session = sshConnectInfo.getjSch().getSession(webSSHData.getUsername(), webSSHData.getHost(), webSSHData.getPort());
        session.setConfig(config);
        //设置密码
        session.setPassword(webSSHData.getPassword());
        //连接  超时时间30s
        session.connect(30000);

        //开启shell通道
        Channel channel = session.openChannel("shell");
        Channel sftp = session.openChannel("sftp");
        ChannelSftp channelSftp = (ChannelSftp) sftp;

        channelSftp.connect(3000);
        //通道连接 超时时间3s
        channel.connect(3000);

        WebShellConnEntity webShellConnEntity = webShellConnRepository.findFirstByHostAndUserName(webSSHData.getHost(),webSSHData.getUsername());
        if (webShellConnEntity == null){
            webShellConnEntity = new WebShellConnEntity();
        }
        webShellConnEntity.setHost(webSSHData.getHost());
        webShellConnEntity.setPort(webSSHData.getPort());
        webShellConnEntity.setUserName(webSSHData.getUsername());
        if (webSSHData.isSavePassword()){
            webShellConnEntity.setPassword(webSSHData.getPassword());
        }else{
            webShellConnEntity.setPassword("");
        }
        webShellConnRepository.save(webShellConnEntity);

        //设置channel
        sshConnectInfo.setChannel(channel);
        sshConnectInfo.setHost(webSSHData.getHost());
        sshConnectInfo.setPort(webSSHData.getPort());
        sshConnectInfo.setUserName(webSSHData.getUsername());
        sshConnectInfo.setPassword(webSSHData.getPassword());

        sendMessage(webSocketSession,ConstantPool.WEBSSH_MESSAGE_TYPE_CONNECT,"".getBytes());

        //读取终端返回的信息流
        InputStream inputStream = channel.getInputStream();
        try {
            //循环读取
            byte[] buffer = new byte[1024];
            int i = 0;
            //如果没有数据来，线程会一直阻塞在这个地方等待数据。
            while ((i = inputStream.read(buffer)) != -1) {
                sendMessage(webSocketSession,ConstantPool.WEBSSH_MESSAGE_TYPE_COMMAND, Arrays.copyOfRange(buffer, 0, i));
            }
        } finally {
            //断开连接后关闭会话
            session.disconnect();
            channel.disconnect();
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    /**
     * 将消息转发到服务器
     * @param channel 服务器连接终端
     * @param command 命令
     * @throws IOException
     */
    private void transToSSH(Channel channel, String command) throws IOException {
        if (channel != null) {
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }
}
