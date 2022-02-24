package com.kiv.kiveiruapi.system.utils;

import com.kiv.kiveiruapi.system.entity.User;

// ThreadLocal获取用户信息
public class UserThreadLocal {

    private UserThreadLocal() {}

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user) {
        LOCAL.set(user);
    }

    public static User get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
