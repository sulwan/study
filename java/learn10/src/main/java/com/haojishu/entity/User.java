package com.haojishu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName(value = "user")
public class User extends Model {

  private Long Id;

  private String username;

  private String email;
}
