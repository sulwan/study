package com.haojishu.controller;

import com.haojishu.Service.IUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

	@Reference(version = "1.0.0", interfaceClass = IUserService.class)
	IUserService iUserService;

	@GetMapping(value = "/")
	public String Hello() {
		String ret = iUserService.sayHello("sulwan");
		System.out.println(ret);
		return "Ok";
	}
}
