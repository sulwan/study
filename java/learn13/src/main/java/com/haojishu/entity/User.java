package com.haojishu.entity;


import lombok.*;

@ToString
@Data
public class User {

	private Integer Id;

	private String username;

	@Getter
	@Setter
	private String email;

	@NonNull
	private String nikename;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
