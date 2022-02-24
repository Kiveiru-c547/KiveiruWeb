package com.kiv.kiveiruapi.xshell.webssh.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 */
public class DownloadUtil {
    private DownloadUtil() {
    }

    /**
     * 文件下载设置文件名编码
     * @param request
     * @param response
     * @param fileName 文件名称
     * @throws UnsupportedEncodingException
     */
    public static void encodeFileName(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        fileName = StringUtils.replace(fileName, "+", "%20");
        if (fileName.length() > 150) {
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
            fileName = StringUtils.replace(fileName, " ", "%20");
        }

        String agent = request.getHeader("USER-AGENT");
        if (agent != null && agent.contains("Mozilla") && !agent.toLowerCase().contains("msie")) {
            response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + fileName + ';');
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ';');
        }

    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @param srcPath 文件路径
     * @throws IOException
     */
    public static void outputToClient(HttpServletRequest request, HttpServletResponse response, String srcPath) throws IOException {
        InputStream in = new FileInputStream(srcPath);
        outputToClient(response,in);
    }


    /**
     * 下载文件
     * @param response
     * @param in
     * @throws IOException
     */
    public static void outputToClient(HttpServletResponse response,InputStream in) throws IOException {
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[8192];
        while(true) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1) {
                in.close();
                out.close();
                return;
            }
            out.write(buffer, 0, bytesRead);
            out.flush();
        }
    }
}
