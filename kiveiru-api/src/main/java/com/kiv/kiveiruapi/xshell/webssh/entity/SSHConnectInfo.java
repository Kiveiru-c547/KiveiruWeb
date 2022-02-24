package com.kiv.kiveiruapi.xshell.webssh.entity;

import com.jcraft.jsch.*;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.Properties;

/**
 * ssh连接信息
 */
public class SSHConnectInfo {
    private WebSocketSession webSocketSession;
    private JSch jSch;
    private Channel channel;
    private String host;
    private String userName;
    private String password;
    private Integer port = 22;



    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public JSch getjSch() {
        return jSch;
    }

    public void setjSch(JSch jSch) {
        this.jSch = jSch;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelSftp getSftpChannel() {
        if (StringUtils.isEmpty(this.host)||StringUtils.isEmpty(this.userName)||StringUtils.isEmpty(this.password)){
            return null;
        }
        //连接  超时时间30s
        try {
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        //获取jsch的会话
        Session  session = getjSch().getSession(this.getUserName(), this.getHost(), this.getPort());
        session.setConfig(config);
        //设置密码
        session.setPassword(getPassword());
        session.connect(30000);
        Channel sftp = session.openChannel("sftp");
        ChannelSftp channelSftp = (ChannelSftp) sftp;
        channelSftp.connect(3000);
        return channelSftp;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
