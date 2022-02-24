package com.kiv.kiveiruapi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.kiv.kiveiruapi.system.common.ErrorCode;
import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.system.entity.LoginParams;
import com.kiv.kiveiruapi.system.entity.User;
import com.kiv.kiveiruapi.system.service.LoginService;
import com.kiv.kiveiruapi.system.service.UserService;
import com.kiv.kiveiruapi.system.utils.JWTUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String salt = "Kiveiru!#@";

    @Override
    public Response login(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //  检查参数是否合法
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Response.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);
        User user = userService.findUser(account, password);
        // 根据用户名和密码去User表中查询是否存在
        if (user == null) {
            return Response.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        // 使用Jwt生成token，返回给前端
        String token = JWTUtils.createToken(user.getId());

        // token放入redis中
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user), 1, TimeUnit.DAYS);

        return Response.success(token);
    }

    @Override
    public Response logout(String token) {
        redisTemplate.delete("Token_"+token);
        return Response.success(null);
    }

    @Override
    public Response register(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        int authority = loginParams.getAuthority();
        // 判断参数是否合法
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Response.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        // 判断账号是否已注册
        User user = userService.findUserByAccount(account);
        if (user != null) {
            return Response.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        user = new User();
        user.setAccount(account);
        user.setPassword(DigestUtils.md5Hex(password + salt));
        user.setAuthority(authority);
        user.setNickname("");
        userService.addUser(user);

        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user), 1, TimeUnit.DAYS);

        return Response.success(token);
    }
}
