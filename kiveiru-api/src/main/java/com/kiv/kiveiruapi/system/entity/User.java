package com.kiv.kiveiruapi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    // 默认id是分布式id，使用了雪花算法
    //@TableId(type = IdType.AUTO) //自增型id
    private Long id;
    private String account;
    private String password;
    private String nickname;
    private Integer authority;
}
