package com.kylin.aop.service;

/**
 * @Author: zhebin
 * @Date: 2019/9/26 9:07
 */
public interface UserService {
    /**
     * 获取用户信息
     * @return
     * @param tel
     */
    String findUserName(String tel);
}
