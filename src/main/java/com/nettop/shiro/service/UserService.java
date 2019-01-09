package com.nettop.shiro.service;

import com.nettop.shiro.entity.User;
import com.nettop.utils.LoginResult;

public interface UserService {
    User findByUserName(String userName);
}
