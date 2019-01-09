package com.nettop.shiro.service;

import com.nettop.utils.LoginResult;

public interface LoginService {
    LoginResult login(String userName, String password);
    void logout();
}