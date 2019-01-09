package com.nettop.demo.service;

import com.nettop.demo.entity.User;

public interface UserMemberService {
    public User getUserById(String userId);

    boolean addUser(User record);
}