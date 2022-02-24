package com.kiv.kiveiruapi.xshell.webssh.controller;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.xshell.webssh.constant.ConstantPool;
import com.kiv.kiveiruapi.xshell.webssh.entity.SSHConnectInfo;
import com.kiv.kiveiruapi.xshell.webssh.entity.WebShellConnEntity;
import com.kiv.kiveiruapi.xshell.webssh.service.WebShellConnService;
import com.kiv.kiveiruapi.xshell.webssh.service.impl.WebSSHServiceImpl;
import com.kiv.kiveiruapi.xshell.webssh.utils.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class RouterController {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private WebShellConnService webShellConnService;
    /**
     * 终端页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/page")
    public String websshpage(HttpServletRequest request, Model model){
        String contextPath = request.getServletContext().getContextPath();
        model.addAttribute("contextPath", contextPath);
        return "webssh";
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,@RequestParam("userId") String userId,@RequestParam(value = "path") String path) throws SftpException, IOException {
        SSHConnectInfo sshConnectInfo = WebSSHServiceImpl.sshMap.get(userId);
        ChannelSftp sftpChannel = sshConnectInfo.getSftpChannel();
        if (sftpChannel != null) {
            InputStream inputStream = sftpChannel.get(path);
            String fileName = path.substring(path.lastIndexOf("/")+1);
            DownloadUtil.encodeFileName(request,response,fileName);
            DownloadUtil.outputToClient(response,inputStream);
        }
    }
    @RequestMapping("/searchHosts")
    @ResponseBody
    public List<WebShellConnEntity> searchHosts(@RequestParam("host") String host){
        return webShellConnService.searchHost(host);
    }

    @RequestMapping("/deleteHost")
    @ResponseBody
    public boolean deleteHost(@RequestParam("id") Long id){
        webShellConnService.deleteHost(id);
        return true;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public void uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("userId") String userId,@RequestParam("filePath")String filePath) throws IOException, SftpException {
        System.out.println(file.getOriginalFilename());
        System.out.println(filePath);
        SSHConnectInfo sshConnectInfo = WebSSHServiceImpl.sshMap.get(userId);
        ChannelSftp sftpChannel = sshConnectInfo.getSftpChannel();
        if (sftpChannel != null) {
            sftpChannel.put(file.getInputStream(),filePath+"/"+file.getOriginalFilename());
        }
    }

    @ResponseBody
    @RequestMapping("/listFile")
    public Response listFile(@RequestParam("userId") String userId,@RequestParam(value = "path",required = false) String path){
        List<Map<String,String>> fileList = new ArrayList<>();
        Map<String,String> local = null;
        Map<String,String> parent = null;
        SSHConnectInfo sshConnectInfo = WebSSHServiceImpl.sshMap.get(userId);
        if (StringUtils.isEmpty(path)){
            path = "/";
        }
        if (sshConnectInfo != null) {
            ChannelSftp sftpChannel = sshConnectInfo.getSftpChannel();
            if (sftpChannel != null) {
                Vector<ChannelSftp.LsEntry> vector = null;
                try {
                    vector = sftpChannel.ls(path);
                } catch (SftpException e) {
                    e.printStackTrace();
                    try {
                        WebSSHServiceImpl.sendMessage(userId, ConstantPool.WEBSSH_MESSAGE_TYPE_ERROR,e.getMessage());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                int index = 0;
                if (vector != null) {
                    for (ChannelSftp.LsEntry entry : vector) {
                        String fileName = entry.getFilename();
                        if (".".equals(fileName)){
                            continue;
                        }
                        String[] attr = entry.getLongname().replaceAll(" ", "#").replaceAll("##", "#").replaceAll("##", "#").replaceAll("##", "#").split("#");
                        Calendar aTime = Calendar.getInstance();
                        aTime.setTimeInMillis(entry.getAttrs().getATime());
                        Calendar mTime = Calendar.getInstance();
                        mTime.setTimeInMillis(entry.getAttrs().getMTime());
                        Map<String,String> map = new HashMap<>();
                        map.put("key",String.valueOf(index++));
                        map.put("fileName",entry.getFilename());
                        map.put("fileAttr",attr[0]);
                        map.put("owner",attr[2]);
                        map.put("ownerGroup",attr[3]);
                        map.put("createTime",sdf.format(aTime.getTime()));
                        map.put("motifyTime",sdf.format(mTime.getTime()));
                        map.put("filePath",path+"/"+fileName);
                        boolean isDir = false;
                        if (entry.getLongname().toLowerCase().startsWith("d")){
                            isDir = true;
                        }
                        map.put("isDir",String.valueOf(isDir));
                        boolean isFile = false;
                        if (entry.getLongname().toLowerCase().startsWith("-")){
                            isFile = true;
                            map.put("size","0");
                        }else{
                            map.put("size",String.valueOf(entry.getAttrs().getSize()));
                        }
                        map.put("isFile",String.valueOf(isFile));
                        if (".".equals(entry.getFilename())){
                            local = map;
                        }else if ("..".equals(entry.getFilename())){
                            parent = map;
                        }else{
                            fileList.add(map);
                        }
                    }
                }
            }
        }
        List<Map<String,String>> res = new ArrayList<>();
        if (local!=null){
            res.add(local);
        }
        if (parent!=null){
            res.add(parent);
        }
        if (fileList.size()>0){
            res.addAll(fileList);
        }
        return Response.success(res);
    }


    /**
     * 文件上传
     * @param file
     * @param filePath
     * @throws IOException
     */
    @RequestMapping(value = "/upload-xshell", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file,@RequestParam("filePath")String filePath) throws IOException {
        File dir = new File(filePath);
        if (!dir.exists()||!dir.isDirectory()){
            dir.mkdirs();
        }
        FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(filePath+"/"+file.getOriginalFilename()));
    }

    /**
     * 文件下载
     * @param request
     * @param response
     * @param filePath
     * @throws IOException
     */
    @RequestMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response,@RequestParam("filePath") String filePath) throws IOException {
        System.out.println(filePath);
        System.out.println(java.net.URLDecoder.decode(filePath, "UTF-8"));
        DownloadUtil.encodeFileName(request,response,new File(filePath).getName());
        File file = new File(filePath);
        if (file.exists()&&!file.isDirectory()){
            DownloadUtil.outputToClient(request,response,filePath);
        }
    }
}
