package com.kiv.kiveiruapi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.kiv.kiveiruapi.system.entity.User;
import com.kiv.kiveiruapi.system.service.TokenService;
import com.kiv.kiveiruapi.system.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public User checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);

        return user;
    }
}
