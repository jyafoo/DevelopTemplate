package org.developtemplate.common.constant;

/**
 * Redis缓存常量
 */

public interface RedisCacheConstant {
    /**
     * 用户注册分布式锁
     */
    public static final String LOCK_USER_REGISTER_KEY = "lock_user-register_key:";

    /**
     * 用户登录缓存标识
     */
    public static final String USER_LOGIN_KEY = "user-login_key:";
}
