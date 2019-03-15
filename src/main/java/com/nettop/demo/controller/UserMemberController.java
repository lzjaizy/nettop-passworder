package com.nettop.demo.controller;

import com.nettop.demo.entity.User;
import com.nettop.demo.service.UserMemberService;
import com.nettop.utils.PageUtils;
import com.nettop.utils.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "UserMemberController", description = "UserMemberController | 通过用户来测试swagger")
@RequestMapping("/user")
public class UserMemberController {
    @Resource
    private UserMemberService userService;

    @Value("${filePath}")
    String filePath;

    @RequestMapping(value = "/showUser", method = RequestMethod.GET)
    @ApiOperation(value="根据用户id查询用户", notes="根据用户id查询用户")
    @ApiParam(name = "id", value = "用户id", required = true)
    @ResponseBody
    public User toIndex(@RequestParam(value = "id") String id){
        String userId = id;
        User user = userService.getUserById(userId);
        return user;
    }

    @RequestMapping (value = "/searchPersonList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> searchPersonList(HttpServletRequest request){
        // 调用service方法，获取人员记录
        List<User> list =  userService.getUsers(PageUtils.getPageInfos(request));
        Map<String, Object> result = PageUtils.getPageResult(list);
        return result;
    }

    @RequestMapping(value="/onlineupload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> onlineupload(@RequestParam("onlinefilename") MultipartFile file) {
        Map<String,String> map=new HashMap<>();
        String fileName = file.getOriginalFilename();
        System.out.println("fileName"+fileName);
        System.out.println("filePath        "+filePath);
        try {
            UploadUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //返回json
        System.out.println("file + success!");
        if(file==null){
            map.put("error","false");
        }
        map.put("success","true");
        return map;
    }

}