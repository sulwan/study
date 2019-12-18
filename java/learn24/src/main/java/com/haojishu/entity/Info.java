package com.haojishu.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Info implements Serializable {
  private static final long serialVersionUID = 527123507509086625L;

  private String addr;

  private String city;
}
