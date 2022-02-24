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
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Response register(@RequestBody LoginParams loginParams) {
        // sso 单点登录， 后期如果把登录注册功能提出去（单独的服务，可以独立提供接口服务）
        User user = UserThreadLocal.get();
        System.out.println(user);
        return loginService.register(loginParams);
    }
}
