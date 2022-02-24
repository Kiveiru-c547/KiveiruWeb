package com.kiv.kiveiruapi.system.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* @date 2022/1/19 17:42
* @description mybatis-plus 分页插件
*/
@Configuration
@MapperScan("com.kiv.kiveiruapi.system.mapper")
@MapperScan("com.kiv.kiveiruapi.xshell.ecsmanager.mapper")
public class MybatisPlusConfig {
    /*分页插件*/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
