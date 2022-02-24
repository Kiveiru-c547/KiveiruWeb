package com.kiv.kiveiruapi.system.controller;

import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.system.entity.LoginParams;
import com.kiv.kiveiruapi.system.entity.User;
import com.kiv.kiveiruapi.system.service.LoginService;
import com.kiv.kiveiruapi.system.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Response login(@RequestBody LoginParams loginParams) {
        // 登录 验证用户 访问用户表
        User user = UserThreadLocal.get();
        System.out.println(user);
        return loginService.login(loginParams);
    }
}
