package com.nettop.demo.service;

import com.github.pagehelper.Page;
import com.nettop.demo.entity.User;


import java.util.List;

public interface UserMemberService {
    public User getUserById(String userId);

    public Page<User> getUsers(User user);

    boolean addUser(User record);
}