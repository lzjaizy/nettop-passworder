package com.nettop.demo.dao;

import com.github.pagehelper.Page;
import com.nettop.demo.entity.User;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("id") String id);

    Page<User> selectAllUsers();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}