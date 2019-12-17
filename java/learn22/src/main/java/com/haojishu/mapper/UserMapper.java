package com.haojishu.mapper;

import com.haojishu.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

  List<UserEntity> getAll();

  UserEntity getOne(Long id);

  void insert(UserEntity userEntity);

  void update(UserEntity userEntity);

  void delete(Long id);
}
