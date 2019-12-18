package com.haojishu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haojishu.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class siteController {

  @Autowired ObjectMapper objectMapper;

  @GetMapping(value = "/")
  public Users hello() throws JsonProcessingException {
    Users users = new Users();
    users.setUsername("sulwan");
    users.setId(2);
    users.setBirthday(new Date());

    System.out.println(objectMapper.writeValueAsString(users));
    return users;
  }

  /** 演示反序列化 */
  @GetMapping(value = "/readJson")
  public String readJson() throws JsonProcessingException {
    String json = "{\"id\":1,\"username\":\"sulwan\"}";
    JsonNode node = objectMapper.readTree(json);
    System.out.println(node);
    String username = node.get("username").toString();
    Integer id = node.get("id").intValue();
    return username + "  " + id;
  }

  /** 演示反序列化多级 */
  @GetMapping(value = "/readJsonMu")
  public String readJsonMut() throws JsonProcessingException {
    String json = "{\"id\":1,\"info\":{\"addr\":\"hebei\",\"city\":\"石家庄\"}}";
    JsonNode node = objectMapper.readTree(json);
    JsonNode info = node.get("info");
    System.out.println(info);
    String addr = info.get("addr").asText();
    return addr;
  }

  /** 把Json解析给实体类 */
  @GetMapping(value = "/readJsonEntity")
  public String readJsonEntity() throws JsonProcessingException {

    String json = "{\"id\":1,\"info\":{\"addr\":\"hebei\",\"city\":\"石家庄\"}}";
    Users users = objectMapper.readValue(json, Users.class);

    String addr = users.getInfo().getAddr();
    return addr;
  }
}
