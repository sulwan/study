package com.haojishu.queue;

import com.haojishu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.*;
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
    User user = new User();
    user.setUsername("sulwan");
    user.setPassword("123456a");
    user.setId(1);
    jmsMessagingTemplate.send(
        "sulwan",
        new MessageCreator() {

          public Message createMessage(Session session) throws JMSException {
            ObjectMessage objMessage = session.createObjectMessage(user);
            return objMessage;
          }
        });
  }

  @Scheduled(fixedRate = 1000)
  public void senMsgTxt() {
    jmsMessagingTemplate.send(
        "sulwan",
        new MessageCreator() {

          public Message createMessage(Session session) throws JMSException {
            TextMessage objMessage = session.createTextMessage("text11111111111");
            return objMessage;
          }
        });
  }
}
