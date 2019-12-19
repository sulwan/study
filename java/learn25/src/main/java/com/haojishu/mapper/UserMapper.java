package com.haojishu.mapper;

import com.haojishu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    List<User> findAll();

    void add(User user);
}
