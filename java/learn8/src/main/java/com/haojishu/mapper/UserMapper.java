package com.haojishu.mapper;

import com.haojishu.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {
  @Select("select * from users")
  @Results({
    @Result(property = "username", column = "username", javaType = String.class),
    @Result(property = "password", column = "password", javaType = String.class)
  })
  List<User> findAll();

  @Select("select * from users where id = #{id}")
  User getOne(Long id);

  @Insert("insert into users (username,password) values (#{username},#{password})")
  void insert(User user);

  @Delete("delete from users where id = #{id}")
  void delete(Long id);
}
