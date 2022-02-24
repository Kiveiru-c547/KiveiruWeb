package com.kiv.kiveiruapi.system.controller;

import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//json数据进行交互
@RestController
@RequestMapping(value = "/user")
public class UserController {
    /*这里通常会新建一个service类，在service类里引入Resource*/
    @Autowired
    private UserService userService;

    @GetMapping("/getInfo")
    public Response getUserInfo(@RequestHeader("Authorization") String token) {
        return userService.findUserByToken(token);
    }
}
