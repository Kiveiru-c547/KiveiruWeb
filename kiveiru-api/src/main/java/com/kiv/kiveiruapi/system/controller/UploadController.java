package com.kiv.kiveiruapi.system.controller;


import com.kiv.kiveiruapi.system.common.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {

    @PostMapping
    public Response upload(@RequestParam("image")MultipartFile file) {
        // 原始文件名称
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + fileName;

        // 图片一般上传到图片服务器

        return null;
    }
}
