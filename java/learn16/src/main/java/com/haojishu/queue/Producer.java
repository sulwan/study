package com.haojishu.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
@EnableScheduling
/** ** 这里就是叫Spring自动加载，使用个定时器没事发送消息，方便演示 */
public class Producer {

  // 这个就是Spring对于Jms的一个封装类
  @Autowired private JmsTemplate jmsMessagingTemplate;
  // 加载我们刚才创建的目的地
  @Autowired private Queue sulwan;

  @Scheduled(fixedRate = 1000)
  public void senMsg() {
    System.out.println("创建一个消息");
    jmsMessagingTemplate.convertAndSend("sulwan", "创建消息了");
  }
}
