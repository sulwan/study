package com.haojishu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"age"})
public class Users implements Serializable {

  private static final long serialVersionUID = -4380431940533975714L;

  private Integer id;

  private String username;

  private Integer age;

  @JsonIgnore private Date birthday;

  @JsonProperty("infos")
  private Info info;
}
