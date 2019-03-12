package com.nettop.demo.service;

import com.nettop.demo.dao.UserMapper;
import com.nettop.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserMemberService")
public class UserMemberServiceImpl implements UserMemberService {

    @Resource
    private UserMapper userMapper;


    public User getUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public boolean addUser(User record){
        boolean result = false;
        try {
            userMapper.insertSelective(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}