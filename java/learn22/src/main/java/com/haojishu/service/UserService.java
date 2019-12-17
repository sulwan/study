package com.haojishu.service;

import com.haojishu.annotation.DataSource;
import com.haojishu.entity.UserEntity;
import com.haojishu.enums.DataSourceType;
import com.haojishu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired UserMapper userMapper;

  public List<UserEntity> getAll() {
    return userMapper.getAll();
  }

  public UserEntity getOne(Long id) {
    return userMapper.getOne(id);
  }

  @DataSource(value = DataSourceType.SLAVE)
  public void insert(UserEntity userEntity) {
    userMapper.insert(userEntity);
  }

  public void update(UserEntity userEntity) {
    userMapper.update(userEntity);
  }

  public void delete(Long id) {
    userMapper.delete(id);
  }
}
