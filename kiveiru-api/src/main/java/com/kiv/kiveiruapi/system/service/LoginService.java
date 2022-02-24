package com.kiv.kiveiruapi.system.service;

import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.system.entity.LoginParams;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Response login(LoginParams loginParams);

    /**
     * 登出功能
     * @param token
     * @return
     */
    Response logout(String token);

    /**
     * 注册功能
     * @param loginParams
     * @return
     */
    Response register(LoginParams loginParams);
}
