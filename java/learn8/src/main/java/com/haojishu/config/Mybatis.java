package com.haojishu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.haojishu.mapper")
public class Mybatis {
}
