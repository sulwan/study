package com.haojishu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojishu.entity.User;
import com.haojishu.mapper.UserMapper;
import com.haojishu.service.UserService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
  @Override
  public List<User> findAllWhereString(String username) {
    return baseMapper.findAllWhereString(username);
  }
}
