package com.haojishu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haojishu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
  List<User> findAllWhereString(String username);
}
