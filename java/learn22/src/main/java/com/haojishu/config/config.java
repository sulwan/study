package com.haojishu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/** 这是一个配置文件 */
@Configuration
/** 指定要扫描的Mapper类的包的路径 */
@MapperScan("com.haojishu.mapper")
public class config {}
