package com.nettop.demo.controller;

import com.nettop.demo.entity.User;
import com.nettop.demo.service.UserMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserMemberController {
    @Resource
    private UserMemberService userService;

    @RequestMapping("/showUser")
    @ResponseBody
    public User toIndex(HttpServletRequest request){
        String userId = request.getParameter("id");
        User user = userService.getUserById(userId);
        return user;
    }

}