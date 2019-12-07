package com.haojishu.controller;

import com.haojishu.entity.UserEntity;
import com.haojishu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class siteController {

  @Autowired UserMapper userMapper;

  // 查询所有数据
  @RequestMapping(value = "/")
  public String hello() {
    List<UserEntity> list = userMapper.getAll();
    System.out.println(list);
    return "hello";
  }

  // 根据条件Id查询
  @RequestMapping(value = "/getOne")
  public UserEntity getOne() {
    UserEntity userEntity = userMapper.getOne(4L);
    return userEntity;
  }

  // 根据条件Id删除
  @RequestMapping(value = "/del")
  public String del() {
    userMapper.getOne(1L);
    return "del";
  }

  // 根据条件更新
  public String update() {
    UserEntity userEntity = new UserEntity();
    userMapper.update(userEntity);
    return "update";
  }
}
