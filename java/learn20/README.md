SpringBoot整合Dubbo



学习这章请先学习上一张，没有上一章，根本没法学习这一章



首先在父目录pom.xml添加依赖
`pom.xml`

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>2.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper</artifactId>
            <version>2.7.3</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
            <type>pom</type>
        </dependency>
```

`log4j.properties`

```properties
log4j.rootLogger=DEBUG,CONSOLE,FILE
log4j.addivity.org.apache=true
filePath=/Users/sulwan/code/log
# 应用于控制台
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.Threshold=INFO
log4j.appender.CONSOLE.Target=System.out
log4j.appender.CONSOLE.Encoding=GBK
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=[framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
# 每天新建日志
log4j.appender.A1=org.apache.log4j.DailyRollingFileAppender
log4j.appender.A1.File=${filePath}/log
log4j.appender.A1.Encoding=GBK
log4j.appender.A1.Threshold=DEBUG
log4j.appender.A1.DatePattern='.'yyyy-MM-dd
log4j.appender.A1.layout=org.apache.log4j.PatternLayout
log4j.appender.A1.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L : %m%n
#应用于文件
log4j.appender.FILE=org.apache.log4j.FileAppender
log4j.appender.FILE.File=${filePath}/file.log
log4j.appender.FILE.Append=false
log4j.appender.FILE.Encoding=GBK
log4j.appender.FILE.layout=org.apache.log4j.PatternLayout
log4j.appender.FILE.layout.ConversionPattern=[framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
# 应用于文件回滚
log4j.appender.ROLLING_FILE=org.apache.log4j.RollingFileAppender
log4j.appender.ROLLING_FILE.Threshold=ERROR
log4j.appender.ROLLING_FILE.File=${filePath}rolling.log
log4j.appender.ROLLING_FILE.Append=true
log4j.appender.CONSOLE_FILE.Encoding=GBK
log4j.appender.ROLLING_FILE.MaxFileSize=10KB
log4j.appender.ROLLING_FILE.MaxBackupIndex=1
log4j.appender.ROLLING_FILE.layout=org.apache.log4j.PatternLayout
log4j.appender.ROLLING_FILE.layout.ConversionPattern=[framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
#自定义Appender
log4j.appender.im=net.cybercorlin.util.logger.appender.IMAppender
log4j.appender.im.host=mail.haojishu.com
log4j.appender.im.username=username
log4j.appender.im.password=password
log4j.appender.im.recipient=sulwan@126.com
log4j.appender.im.layout=org.apache.log4j.PatternLayout
log4j.appender.im.layout.ConversionPattern=[framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
#应用于socket
log4j.appender.SOCKET=org.apache.log4j.RollingFileAppender
log4j.appender.SOCKET.RemoteHost=localhost
log4j.appender.SOCKET.Port=5001
log4j.appender.SOCKET.LocationInfo=true
# Set up for Log Facter 5
log4j.appender.SOCKET.layout=org.apache.log4j.PatternLayout
log4j.appender.SOCET.layout.ConversionPattern=[start]%d{DATE}[DATE]%n%p[PRIORITY]%n%x[NDC]%n%t[THREAD]%n%c[CATEGORY]%n%m[MESSAGE]%n%n  
# Log Factor 5 Appender
log4j.appender.LF5_APPENDER=org.apache.log4j.lf5.LF5Appender
log4j.appender.LF5_APPENDER.MaxNumberOfRecords=2000
# 发送日志给邮件
log4j.appender.MAIL=org.apache.log4j.net.SMTPAppender
log4j.appender.MAIL.Threshold=FATAL
log4j.appender.MAIL.BufferSize=10
log4j.appender.MAIL.From=sulwan@126.com
log4j.appender.MAIL.SMTPHost=www.haojishu.com
log4j.appender.MAIL.Subject=Log4J Message
log4j.appender.MAIL.To=sulwan@126.com
log4j.appender.MAIL.layout=org.apache.log4j.PatternLayout
log4j.appender.MAIL.layout.ConversionPattern=[framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
```

相关文章：一、启动时检查(check)

　　默认情况下dubbo是开启自动检查的,即当项目启动时会自动检查其依赖的服务是否开启,如果没开是会阻止spring的初始化的,即check=true;我们可以将check置为false来关闭启动时检查,如我们在测试或者对其他服务没有依赖的时候可以关闭检查;在springboot中我们可以进行如下配置来关闭启动时检查：

　　1、关闭某个服务的启动检查

　　在引用该服务的@Reference注解上添加check=false,即@Reference(check = false)

　　2、关闭所有服务的启动时检查

　　在application.properties中添加dubbo.consumer.check=false

　　二、超时(timeout,默认为1000)，重试次数(retries)

　　超时：当消费者调用提供者时由于网络等原因有可能会造成长时间拿不到响应,而请求还在不断的发过来这就有可能造成线程阻塞,使用timeout设置超时时间当超过该时间就会抛出异常；设置如下：

　　　　  当只针对某个服务时：@Reference(timeout=XXX)　　

　　　　  当针对所有服务时：dubbo.consumer.timeout=XXX　 

　　重试次数：当调用失败或超时后重新尝试调用的次数,其值不包含第一次

　　　　　当只针对某个服务时：@Reference(retries=XXX)

　　　　  当针对所有服务时:dubbo.consumer.retries=XXX

　　三、多版本(灰色发布)

　　当出现系统版本升级时,新版本有可能不够稳定,这时候可以通过设置version来进行平滑的过渡,下面是dubbo官网的版本迁移步骤：

　　　　在低压力时间段，先升级一半提供者为新版本，再将所有消费者升级为新版本，然后将剩下的一半提供者升级为新版本

　　　　而新旧版本我们可以通过version来定义,假设老版本的version=1.0.0新版本的version=2.0.0；

　　　　老版本服务提供者：@Service(version='1.0.0')

　　　　新版本服务提供者：@Service(version='2.0.0')

　　　　老版本服务消费者：@Reference(version='1.0.0')

　　　　新版本服务消费者：@Reference(version='2.0.0')

　　　　这样新旧版本就完美错开,只会调用version对应的服务,如果调用的时候不需要区分版本号,则进行如下配置：@Reference(version='*'),这样dubbo会默认在新老版本中随机调用

　　四、本地存根(stub)

　　　　正常情况下,服务搭建成功后服务的实现一般都在服务端,但有时候我们可能需要在客户端做些逻辑操作,比如参数验证,缓存处理以及调用失败后伪造容错数据的处理等等,这时候我们就需要用到dubbo的本地存根机制,他能在远程服务调用前在客户端进行相关的逻辑操作，具体步骤如下：

　　　　1、在客户端写一个远程调用服务的实现,并生成有参构造参数为远程服务,代码如下：

```java
package com.darling.boot.order.service;

import com.darling.pubIn.bean.User;
import com.darling.pubIn.service.UserService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *   @author 董琳琳
 *   @date 2018/10/10 15:11
 *   @description   userService的本地存根实现
 */
public class UserServiceStub implements UserService{
    private final UserService userService;

    /**
     * 有参构造 dubbo会自动将远程Userservice注入进来
     * @param userService
     */
    public UserServiceStub(UserService userService) {
        this.userService = userService;
    }


    /**
     * 在远程调用前可以进行判断
     * @param userId
     * @return
     */
    @Override
    public List<User> getUserAddressList(String userId) {
        // 如果userId不为空则进行远程调用 否则不调
        if(!StringUtils.isEmpty(userId)) {
            return userService.getUserAddressList(userId);
        }
        return null;
    }

    @Override
    public void sayHello() {

    }
}
```

 　2、进行存根配置

　　　　在客户端调用远程服务的注解上添加stub,值为我们客户端实现的远程服务类的全路径名,如@Reference(stub = "com.darling.boot.order.service.UserServiceStub")

 　五、负载均衡

　　　　dubbo提供了四种负载均衡策略,分别是：

　　　　1、Random LoadBalance 按权重的随机负载均衡,也是dubbo默认的负载均衡策略

　　　　2、RoundRobin LoadBalance 按权重的轮询负载均衡,即在轮询的基础上添加了权重的策略

　　　　3、LeastActive LoadBalance 最少活跃调用数,相同活跃数的随机访问,活跃数指调用前后的计数差即响应时间的长短;这种策略可以使响应慢的提供者收到的请求较少,大大提供系统性能

　　　　4、ConsistentHash LoadBalance 一致性哈希；相同参数的请求总是发到同一提供者

　　　　负载均衡的配置：@Reference(loadbalance = "roundrobin")，loadbalance 的值即为四种负载均衡的名称,全部小写

　　六、服务降级

　　　　当服务器压力过大时,我们可以通过服务降级来使某些非关键服务的调用变得简单,可以对其直接进行屏蔽,即客户端不发送请求直接返回null,也可以正常发送请求当请求超时或不可达时再返回null;服务降级的相关配置可以直接在dubbo-admin的监控页面进行配置;通常是基于消费者来配置的,在dubbo-admin找到对应的消费者想要降级的服务,点击其后面的屏蔽或容错按钮即可生效；其中,屏蔽按钮点击表示放弃远程调用直接返回空,而容错按钮点击表示继续尝试进行远程调用当调用失败时再返回空

　　七、集群容错

　　　　在集群调用失败时，Dubbo 提供了多种容错方案，缺省为 failover 重试。下面列举dubbo支持的容错策略：

　　　　Failover Cluster：失败自动切换，当出现失败，重试其它服务器。通常用于读操作，但重试会带来更长延迟。可通过 retries="XXX" 来设置重试次数(不含第一次)。

　　　　Failfast Cluster：快速失败，只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录。

　　　　Failsafe Cluster：失败安全，出现异常时，直接忽略。通常用于写入审计日志等操作。

　　　　Failback Cluster：失败自动恢复，后台记录失败请求，定时重发。通常用于消息通知操作。

　　　　Forking Cluster：并行调用多个服务器，只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 forks="2" 来设置最大并行数。

　　　　Broadcast Cluster：广播调用所有提供者，逐个调用，任意一台报错则报错 [2]。通常用于通知所有提供者更新缓存或日志等本地资源信息。

 　　　配置如下：@Reference(cluster = "failsafe")这里表示使用失败安全的容错策略

　　　　还有一种springcloud默认的容错策略Hystrix；Hystrix旨在通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备拥有回退机制和断路器功能的线程和信号隔离，请求缓存和请求打包，以及监控和配置等功能,下面介绍如何整合Hystrix：

　　　　1、分别在提供者和消费者添加Hystrix依赖,或者直接在公共接口层添加,代码如下：

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
  <version>1.4.4.RELEASE</version>
</dependency>
```

　　　　2、在提供者和消费者的启动类上添加@EnableHystrix来启用hystrix；

　　　　3、在提供者需要容错的方法上添加@HystrixCommand表示使用hystrix代理：

```java
 @HystrixCommand // 开启Hystrix代理
    @Override
    public List<User> getUserAddressList(String userId) {
        ArrayList list = new ArrayList();
        list.add(new User(3,"韦德3","男",36,"迈阿密"));
        list.add(new User(23,"詹姆斯23","男",34,"洛杉矶"));
        list.add(new User(24,"科比24","男",39,"洛杉矶"));
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return list;
    } 
```

　　　　4、在消费者需要容错的方法上添加@HystrixCommand,还可以给其添加属性,表示调用出错后处理错误的方法：

```java
package com.darling.boot.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.darling.pubIn.bean.User;
import com.darling.pubIn.service.OrderService;
import com.darling.pubIn.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *   @author 董琳琳
 *   @date 2018/10/8 11:42
 *   @description
 */
@Service
public class OrderServiceimpl implements OrderService {

    @Reference(version = "*",stub = "com.darling.boot.order.service.UserServiceStub",cluster = "")
    UserService service;

    @HystrixCommand(fallbackMethod = "handlerException")
    @Override
    public List<User> initOrder(String userId) {
        return service.getUserAddressList(userId);
    }

    /**
     * 表示调用出错后进行的处理,这里没有处理直接返回null
     * @param userId
     * @return
     */
    public List<User> handlerException(String userId) {
        return null;
    }
}
```

 　在整合hystrix 时发现一个奇怪的现象,就是当你一旦在消费端整合hystrix 后,只要你发送的请求没有进行远程调用,即使该请求没有出错并且正常返回了还是会进入fallbackMethod指定的容错方法,百思不得其解, 准备后期去网上问问大神们

 　由于时间关系,我对照官网就实现了以上几种配置,还有许多其他配置没有使用,希望在以后的工作中能有机会用到,到时候再回来记录,这篇笔记的dubbo属性配置有个值得注意的地方,就是我几乎所有的配置都是在引用服务的注解@Reference上进行的,而dubbo支持多种配置,除了全局配置还可以精确到给具体的类具体的方法进行配置,也可以给提供者和消费者进行配置,下面记录dubbo的配置原则：

　　dubbo推荐在Provider上尽量多配置Consumer端属性,原因如下：

　　1、作服务的提供者，比服务使用方更清楚服务性能参数，如调用的超时时间，合理的重试次数，等等

　　2、在Provider配置后，Consumer不配置则会使用Provider的配置值，即Provider配置可以作为Consumer的缺省值。否则，Consumer会使用Consumer端的全局设置，这对于Provider不可控的，并且往往是不合理的

　　配置优先级：　方法级优先，接口级次之，全局配置再次之；如果级别一样，则消费方优先，提供方次之