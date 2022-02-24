package com.kiv.kiveiruapi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kiv.kiveiruapi.system.common.ErrorCode;
import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.system.entity.User;
import com.kiv.kiveiruapi.system.mapper.UserMapper;
import com.kiv.kiveiruapi.system.service.TokenService;
import com.kiv.kiveiruapi.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TokenService tokenService;

    @Resource
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User findUser(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.eq(User::getPassword, password);
        queryWrapper.select(User::getAccount, User::getId, User::getNickname);
        queryWrapper.last("limit 1");

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Response findUserByToken(String token) {
        User user = tokenService.checkToken(token);
        if (user == null) {
            Response.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }

        return Response.success(user);
    }

    @Override
    public User findUserByAccount(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.last("limit 1");

        return userMapper.selectOne(queryWrapper);
    }
}
