package com.haojishu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haojishu.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

  List<User> findAllWhereString(String username);
}
