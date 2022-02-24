package com.kiv.kiveiruapi.system.service;

import com.kiv.kiveiruapi.system.entity.User;

public interface TokenService {
    User checkToken(String token);
}
