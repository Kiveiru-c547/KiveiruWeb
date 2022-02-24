package com.kiv.kiveiruapi.system.handler;

import com.alibaba.fastjson.JSON;
import com.kiv.kiveiruapi.system.common.ErrorCode;
import com.kiv.kiveiruapi.system.common.Response;
import com.kiv.kiveiruapi.system.entity.User;
import com.kiv.kiveiruapi.system.service.TokenService;
import com.kiv.kiveiruapi.system.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 登录拦截器
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (! (handler instanceof HandlerMethod)) {
            // 如果handler不是HandlerMethod（有可能是RequestResourcesHandler） 访问静态资源 默认去classpath下的static目录查询
            return true;
        }

        String token = request.getHeader("Authorization");

        log.info("===================request start========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("===================request end==========================");

        // 判断token是否为空 token空 未登录
        if (StringUtils.isBlank(token)) {
            Response res = Response.fail(ErrorCode.NO_LOGIN.getCode(), "当前用户未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(res));
            return false;
        }
        // 如果token不为空 登录验证 loginService.checkToken
        User user = tokenService.checkToken(token);
        if (user == null) {
            Response res = Response.fail(ErrorCode.NO_LOGIN.getCode(), "当前用户未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(res));
            return false;
        }

        // 在controller中直接获取用户信息
        UserThreadLocal.put(user);

        // 登录成功 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        // 如果不删除ThreadLocal中用完的信息 会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
