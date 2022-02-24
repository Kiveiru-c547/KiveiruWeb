package com.kiv.kiveiruapi.system.service;

import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.system.entity.User;

public interface UserService {
    /**
     * 添加用户
     * @param user
     * @return
     */
    void addUser(User user);

    /**
     * 根据账号密码查找用户信息
     * @param account
     * @param password
     * @return
     */
    User findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Response findUserByToken(String token);

    /**
     * 根据账号查找用户信息
     * @param account
     * @return
     */
    User findUserByAccount(String account);
}
