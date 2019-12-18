package com.haojishu.controller;

import com.haojishu.entity.Users;
import com.haojishu.service.Impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class siteController {

  @Autowired UsersServiceImpl usersService;

  @RequestMapping(value = "/")
  public String hello() {
    Users users = new Users();
    users.setUsername("测试数据");
    usersService.inster(users);

    return "填充数据成功";
  }

  @RequestMapping(value = "/list")
  public String getAll() {
    List<Users> list = usersService.findAll();
    System.out.println(list);
    return "list";
  }

  @RequestMapping(value = "/list2")
  public String getAll2() {
    List<Users> list = usersService.findAllTwo();
    System.out.println(list);
    return "list2";
  }
}
