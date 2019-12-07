package com.haojishu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("用户实体类")
@Data
public class User implements Serializable {

  @ApiModelProperty("用户Id")
  private Integer Id;

  @ApiModelProperty("用户名称")
  private String username;
}
