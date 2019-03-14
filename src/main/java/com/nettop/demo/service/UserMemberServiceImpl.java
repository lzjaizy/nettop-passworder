package com.nettop.demo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nettop.demo.dao.UserMapper;
import com.nettop.demo.entity.PageEntity;
import com.nettop.demo.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserMemberService")
public class UserMemberServiceImpl implements UserMemberService {

    @Resource
    private UserMapper userMapper;


    public User getUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public Page<User> getUsers(PageEntity pageEntity) {
        PageHelper.startPage(pageEntity.getPageNo(),pageEntity.getPageSize());
        return userMapper.selectAllUsers();
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