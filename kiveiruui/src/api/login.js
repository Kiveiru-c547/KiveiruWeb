import request from "@/utils/request";

// 注册
export function register(data) {
    return request({
        url: 'register',
        method: 'post',
        data: data
    })
}

//登录
export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data: data
    });
}

// 获取用户信息
export function getUserInfo() {
    return request({
        url: '/user/getInfo',
        method: 'get',
    })
}

// 登出
export function logout() {
    return request({
        url: '/logout',
        method: 'get'
    });
}