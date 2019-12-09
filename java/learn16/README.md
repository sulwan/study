SpringBoot整合Activemq

ActiveMQ 是Apache出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持JMS1.1和J2EE 1.4规范的 JMS Provider实现，尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位。

特性

多种语言和协议编写客户端。语言: Java,C,C++,C#,Ruby,Perl,Python,PHP。应用协议： OpenWire,Stomp REST,WS Notification,XMPP,AMQP
完全支持JMS1.1和J2EE 1.4规范 （持久化，XA消息，事务)
对Spring的支持，ActiveMQ可以很容易内嵌到使用Spring的系统里面去，而且也支持Spring2.0的特性
通过了常见J2EE服务器（如 Geronimo,JBoss 4,GlassFish,WebLogic)的测试，其中通过JCA 1.5 resource adaptors的配置，可以让ActiveMQ可以自动的部署到任何兼容J2EE 1.4 商业服务器上
支持多种传送协议：in-VM,TCP,SSL,NIO,UDP,JGroups,JXTA
支持通过JDBC和journal提供高速的消息持久化
从设计上保证了高性能的集群，客户端-服务器，点对点
支持Ajax
支持与Axis的整合
可以很容易的调用内嵌JMS provider，进行测试

Activemq支持两种消息传送模式：PERSISTENT （持久消息）和 NON_PERSISTENT（非持久消息）

从字面意思就可以了解，这是两种正好相反的模式。

> mq主要有2种监听方式，一种是队列Queue，另外一种是主题Topic 

1、PERSISTENT 持久消息

是activemq默认的传送方式，此方式下的消息在配合activemq.xml中配置的消息存储方式，会被存储在特定的地方，直到有消费者将消息消费或者消息过期进入DLQ队列，消息生命周期才会结束。

此模式下可以保证消息只会被成功传送一次和成功使用一次，消息具有可靠性。在消息传递到目标消费者，在消费者没有成功应答前，消息不会丢失。所以很自然的，需要一个地方来持久性存储。

如果消息消费者在进行消费过程发生失败，则消息会被再次投递。

2、NON_PERSISTENT 非持久消息

非持久的消息适用于不重要的，可以接受消息丢失的哪一类消息，这种消息只会被投递一次，消息不会在持久性存储中存储，也不会保证消息丢失后的重新投递。

消息的签收模式：

客户端成功接收一条消息的标志是一条消息被签收，成功应答。

消息的签收情形分两种：

1、带事务的session

 如果session带有事务，并且事务成功提交，则消息被自动签收。如果事务回滚，则消息会被再次传送。

2、不带事务的session

 不带事务的session的签收方式，取决于session的配置。

Activemq支持一下三种模式：

  Session.AUTO_ACKNOWLEDGE  消息自动签收
  Session.CLIENT_ACKNOWLEDGE  客户端调用acknowledge方法手动签收
  Session.DUPS_OK_ACKNOWLEDGE 不必必须签收，消息可能会重复发送。在第二次重新传递消息的时候，消息头的        JmsDelivered会被置为true标示当前消息已经传送过一次，客户端需要进行消息的重复处理控制。

`POM.XML`

```xml
<!--添加activemq-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```