package com.haojishu.controller;

import com.haojishu.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

	@RequestMapping(value = "/")
	public User hello() {
		User user = new User("sulwan");
		user.setEmail("sulwan@126.com");
		return user;
	}
}