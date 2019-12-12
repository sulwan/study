package com.haojishu.Service;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(version = "1.0.0", timeout = 10000, interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public String sayHello(String username) {
		return "Hello " + username;
	}
}
