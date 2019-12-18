package com.haojishu.mapper;

import com.haojishu.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UsersMapper {
  List<Users> getAll();

  void insert(Users users);
}
