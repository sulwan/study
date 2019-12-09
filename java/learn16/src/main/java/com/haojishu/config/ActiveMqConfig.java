package com.haojishu.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class ActiveMqConfig {
  // 配置队列目的地
  @Bean(name = "sulwan")
  public ActiveMQQueue sulwan() {
    ActiveMQQueue activeMQQueue = new ActiveMQQueue("sulwan");
    return activeMQQueue;
  }
}
