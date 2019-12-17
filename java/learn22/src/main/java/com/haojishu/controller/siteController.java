package com.haojishu.controller;

import com.haojishu.entity.UserEntity;
import com.haojishu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class siteController {

  @Autowired UserService userService;

  // 查询所有数据
  @RequestMapping(value = "/")
  public String hello() {
    List<UserEntity> list = userService.getAll();
    System.out.println(list);
    return "查询主库存在";
  }

  // 根据条件Id查询
  @RequestMapping(value = "/add")
  public String getOne() {
    UserEntity entity = new UserEntity();
    entity.setUsername("从库数据");
    userService.insert(entity);
    return "插入成功，主库不存在";
  }
}
