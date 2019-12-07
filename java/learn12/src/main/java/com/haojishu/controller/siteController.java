package com.haojishu.controller;

import com.haojishu.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("演示文档标注")
public class siteController {

  @ApiOperation("返回Hello字符串")
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String hello() {
    return "hello";
  }

  @ApiOperation("返回用户信息")
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  @ApiImplicitParam(name = "User", value = "用户信息", dataType = "User")
  public User helloUser(User user) {
    return user;
  }
}
