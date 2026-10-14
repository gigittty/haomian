package com.gigittty.haomian.constant;

/**
 * Redis 常量
 */
public interface RedisConstant {
    /**
     * 用户签到的 Redis 键前缀
     */
    String USER_SIGN_IN_KEY_PREFIX = "user:signins";

    /**
     * 获取用户签到的 Redis 键
     *
     * @param year 年份
     * @param userId 用户 ID
     * @return 用户签到的 Redis 键
     */
    static String getUserSignInRedisKey(int year, long userId) {
        return String.format("%s:%s:%S", USER_SIGN_IN_KEY_PREFIX, userId, year);
    }

}
