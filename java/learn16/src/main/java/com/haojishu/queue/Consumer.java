package com.haojishu.queue;

import com.haojishu.entity.User;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

@Service
public class Consumer {

  @JmsListener(destination = "sulwan")
  public void receiveMsg(Message message) throws JMSException {
    if (message instanceof ObjectMessage) {
      ObjectMessage objMessage = (ObjectMessage) message;
      try {
        Object obj = objMessage.getObject();
        User user = (User) obj;
        System.out.println("接收到一个ObjectMessage，包含User对象。");
        System.out.println(user);
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
    if (message instanceof TextMessage) {
      System.out.println("接收到一个字符");
      TextMessage textMessage = (TextMessage) message;
      String txt = textMessage.getText();
      System.out.println(txt);
    }
  }
}
