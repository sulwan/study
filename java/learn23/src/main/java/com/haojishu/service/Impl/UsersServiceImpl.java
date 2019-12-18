package com.haojishu.service.Impl;

import com.haojishu.entity.Users;
import com.haojishu.mapper.UsersMapper;
import com.haojishu.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
  @Autowired UsersMapper usersMapper;

  public List<Users> findAll() {
    return usersMapper.getAll();
  }

  public List<Users> findAllTwo() {
    return usersMapper.getAll();
  }

  public void inster(Users users) {
    usersMapper.insert(users);
  }
}
