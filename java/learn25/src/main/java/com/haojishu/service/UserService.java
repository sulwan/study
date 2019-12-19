package com.haojishu.service;

import com.haojishu.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

  List<User> findAll();

  void add(User user);
}
