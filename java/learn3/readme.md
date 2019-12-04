本章讲解主要内容是如何实现多态机器登陆共享



在有其他语言基础的朋友，一定想到了mysql，redis来存储session，我们在这里要讲解的是redis共享session来实现登陆共享，本文不涉及redis的集群不在本文讨论范围

创建项目依赖于之前文章，请自行查阅learn1的内容

pom.xml

```xml
        <!--Redis基本支持-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
        <!--Session支持-->
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
        </dependency>
```

添加配置文件

SessionConfig.java

```java
package com.haojishu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}
```

>  maxInactiveIntervalInSeconds: 设置 Session 失效时间，使用 Redis Session 之后，原 Spring Boot 的 server.session.timeout 属性不再生效 

Session使用

```java
package com.haojishu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class siteController {

  @RequestMapping(value = "/")
  public String helo(HttpSession session) {
    String username = (String) session.getAttribute("username");
    if (username == null) {
      username = "sulwan";
    }
    session.setAttribute("username", username);
    return "hello redis";
  }
}
```

