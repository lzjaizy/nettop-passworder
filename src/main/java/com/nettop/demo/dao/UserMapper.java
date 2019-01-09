package com.nettop.demo.dao;

import com.nettop.demo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("id") String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}