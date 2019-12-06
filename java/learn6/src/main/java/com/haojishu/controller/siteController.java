package com.haojishu.controller;

import com.haojishu.domain.Users;
import com.haojishu.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class siteController {

  @Autowired UsersService usersService;

  // 首页
  @GetMapping(value = {"/", "/index.html"})
  public String index(Model model) {
    model.addAttribute("title", "标题");
    return "index";
  }

  // 用户列表
  @GetMapping(value = {"/list"})
  public String list(
      @RequestParam(value = "page", defaultValue = "0") Integer pageNumber, Model model) {
    List<Users> list = usersService.findAll(pageNumber, 2);
    Long count = usersService.count();
    boolean hasPrev = pageNumber > 1;
    boolean hasNext = (pageNumber * 2) < count;
    model.addAttribute("contacts", list);
    System.out.println(list);
    model.addAttribute("hasPrev", hasPrev);
    model.addAttribute("prev", pageNumber - 1);
    model.addAttribute("hasNext", hasNext);
    model.addAttribute("next", pageNumber + 1);
    return "list";
  }

  // 删除用户
  @GetMapping(value = {"/del"})
  public String del(Users users) {
    usersService.del(users.getId());
    return "redirect:/list";
  }

  // 保存添加信息
  @GetMapping(value = {"/save"})
  public String save(Users users) {
    usersService.save(users);
    return "redirect:/list";
  }

  // 保存编辑信息
  @GetMapping(value = {"/saveUpdate"})
  public String saveUpdate(Users users) {
    usersService.update(users);

    return "redirect:/list";
  }
}
