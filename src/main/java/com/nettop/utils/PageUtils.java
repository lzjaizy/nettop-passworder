package com.nettop.utils;

import com.github.pagehelper.PageInfo;
import com.nettop.demo.entity.PageEntity;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtils {

  public static PageEntity getPageInfos(HttpServletRequest request){
    PageEntity pageEntity = new PageEntity();

    // page 为easyui分页插件默pageNo认传到后台的参数，代表当前的页码，起始页为1
    String pageNo = request.getParameter("page");

    // rows为为easyui分页插件默认传到后台的参数，代表当前设置的每页显示的记录条数
    String pageSize = request.getParameter("rows");

    if (!StringUtils.isEmpty(pageNo)){
      pageEntity.setPageNo(Integer.parseInt(pageNo));
    }

    if (!StringUtils.isEmpty(pageSize)){
      pageEntity.setPageSize(Integer.parseInt(pageSize));
    }

    return pageEntity;
  }

  public static Map<String,Object> getPageResult(List list){
    // 定义map
    Map<String, Object> jsonMap = new HashMap<String, Object>();

    PageInfo pageInfo = new PageInfo(list);

    // 获取总记录数
    long total = pageInfo.getTotal();

    // total 存放总记录数
    jsonMap.put("total", total);

    // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
    jsonMap.put("rows", list);

    return jsonMap;
  }
}
