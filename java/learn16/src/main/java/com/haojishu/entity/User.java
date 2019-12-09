package com.haojishu.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
  private Integer Id;
  private String username;
  private String password;
}
