package com.haojishu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

  @Autowired StringRedisTemplate stringRedisTemplate;
  @Autowired RedisTemplate redisTemplate;

  @RequestMapping(value = "/")
  @Cacheable(value = "user-key")
  public String hello() {
    // 写入数据
    stringRedisTemplate.opsForValue().set("sulwan", "123456a");
    // 读取数据
    String stringValue = stringRedisTemplate.opsForValue().get("sulwan");
    System.out.println(stringValue.equals("123456a"));

    return "Hello Redis";
  }
}
