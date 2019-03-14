package com.nettop.demo.controller;

import com.github.pagehelper.PageInfo;
import com.nettop.demo.entity.PageEntity;
import com.nettop.demo.entity.User;
import com.nettop.demo.service.UserMemberService;
import com.nettop.utils.PageUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping (value = "searchPersonList")
    @ResponseBody
    public Map<String,Object> searchPersonList(HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();

        // 调用service方法，获取人员记录
        List<User> list =  userService.getUsers(PageUtils.getPageInfos(request));
        result = PageUtils.getPageResult(list);
        return result;
    }
}