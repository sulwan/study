package com.haojishu.controller;

import com.haojishu.domain.User;
import com.haojishu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class siteController {

  @Autowired UserMapper userMapper;

  // 读取所有信息
  @RequestMapping(value = "/")
  public List<User> hello() {
    return userMapper.findAll();
  }

  // 读取单条信息
  @RequestMapping(value = "/one")
  public User getOne(User user) {
    return userMapper.getOne(1L);
  }

  // 插入一条数据
  @RequestMapping(value = "/add")
  public String add() {
    User user = new User();
    user.setEmail("sulwan@126.com");
    user.setPassword("654321");
    user.setUsername("hahaha");
    userMapper.insert(user);
    return "添加成功";
  }
}
