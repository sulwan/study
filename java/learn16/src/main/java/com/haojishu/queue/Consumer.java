package com.haojishu.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

  @JmsListener(destination = "sulwan")
  public void receiveMsg(String text) {
    System.out.println("收到消息了：" + text);
  }
}
