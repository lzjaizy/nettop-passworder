package com.nettop.demo.entity;

public class PageEntity {

  Integer pageNo;

  Integer pageSize;

  String sort;

  public Integer getPageNo() {
    return pageNo;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public String getSort() {
    return sort;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }
}
