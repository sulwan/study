package com.haojishu.service.Impl;

import com.haojishu.entity.User;
import com.haojishu.mapper.UserMapper;
import com.haojishu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

  @Autowired UserMapper userMapper;

  @Override
  public List<User> findAll() {
    return userMapper.findAll();
  }

  @Override
  public void add(User user) {
    userMapper.add(user);
  }
}
