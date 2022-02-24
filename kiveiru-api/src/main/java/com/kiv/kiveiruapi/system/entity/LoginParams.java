package com.kiv.kiveiruapi.system.entity;

import lombok.Data;

@Data
public class LoginParams {

    private String account;
    private String password;
    private int authority;
}
