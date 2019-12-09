package com.haojishu.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class ActiveMqConfig {

  @Value("${spring.activemq.user}")
  private String user;

  @Value("${spring.activemq.password}")
  private String password;

  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Value("${spring.activemq.use-exponential-back-off}")
  private Boolean useExponentialBackOff;

  @Value("${spring.activemq.maximum-redeliveries}")
  private Integer maximumRedeliveries;

  @Value("${spring.activemq.initial-redelivery-delay}")
  private Integer initialRedeliveryDelay;

  @Value("${spring.activemq.back-off-multiplier}")
  private Integer backOffMultiplier;

  @Value("${spring.activemq.use-collision-avoidance}")
  private Boolean useCollisionAvoidance;

  @Value("${spring.activemq.maximum-redelivery-delay}")
  private Integer maximumRedeliveryDelay;

  // 配置队列目的地
  @Bean(name = "sulwan")
  public ActiveMQQueue sulwan() {
    ActiveMQQueue activeMQQueue = new ActiveMQQueue("sulwan");
    return activeMQQueue;
  }

  @Bean
  public RedeliveryPolicy redeliveryPolicy() {
    RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
    // 是否在每次尝试重新发送失败后,增长这个等待时间
    redeliveryPolicy.setUseExponentialBackOff(useExponentialBackOff);
    // 重发次数,默认为6次   这里设置为10次
    redeliveryPolicy.setMaximumRedeliveries(maximumRedeliveries);
    // 重发时间间隔,默认为1秒
    redeliveryPolicy.setInitialRedeliveryDelay(initialRedeliveryDelay);
    // 第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
    redeliveryPolicy.setBackOffMultiplier(backOffMultiplier);
    // 是否避免消息碰撞
    redeliveryPolicy.setUseCollisionAvoidance(useCollisionAvoidance);
    // 设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
    redeliveryPolicy.setMaximumRedeliveryDelay(maximumRedeliveryDelay);
    return redeliveryPolicy;
  }

  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory(RedeliveryPolicy redeliveryPolicy) {
    ActiveMQConnectionFactory activeMQConnectionFactory =
        new ActiveMQConnectionFactory(user, password, brokerUrl);
    activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
    return activeMQConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate();
    // 进行持久化配置 1表示非持久化，2表示持久化
    jmsTemplate.setDeliveryMode(2);
    jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
    // 客户端签收模式
    jmsTemplate.setSessionAcknowledgeMode(4);
    return jmsTemplate;
  }

  @Bean
  public JmsListenerContainerFactory<?> jmsListenerContainerTopic(
      ActiveMQConnectionFactory activeMQConnectionFactory) {
    DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    bean.setPubSubDomain(true);
    bean.setConnectionFactory(activeMQConnectionFactory);
    return bean;
  }

  @Bean
  public JmsListenerContainerFactory<?> jmsListenerContainerQueue(
      ActiveMQConnectionFactory activeMQConnectionFactory) {
    DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    bean.setConnectionFactory(activeMQConnectionFactory);
    return bean;
  }
}
