package com.haojishu.service;

import com.haojishu.entity.Users;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/** 指定改缓存保存位置 */
@CacheConfig(cacheNames = "UsersService")
public interface UsersService {
  @CachePut
  public List<Users> findAll();

  @Cacheable
  public List<Users> findAllTwo();

  public void inster(Users users);
}
