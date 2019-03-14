package com.nettop.demo.controller;

import com.github.pagehelper.PageInfo;
import com.nettop.demo.entity.PageEntity;
import com.nettop.demo.entity.User;
import com.nettop.demo.service.UserMemberService;
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
    public ResponseEntity searchPersonList(User user){
        Map<String, String> map = new HashMap<String, String>();

//        // page 为easyui分页插件默认传到后台的参数，代表当前的页码，起始页为1
//        String pageNo = request.getParameter("page");
//
//        // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
//        String pageSize = request.getParameter("rows");

        // 调用service方法，获取人员记录
        List<User> list =  userService.getUsers(user);

        PageInfo pageInfo = new PageInfo(list);

        // 获取总记录数
        long total = pageInfo.getTotal();

        // 定义map
//        Map<String, Object> jsonMap = new HashMap<String, Object>();
//
//        // total 存放总记录数
//        jsonMap.put("total", total);
//
//        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
//        jsonMap.put("rows", list);
        return new ResponseEntity( new PageEntity<>(list), HttpStatus.OK);
    }
}