整合Mybatis

Jpa与Mybatis各有各的优点，喜欢用那个就用那个，本人更亲近于mybatis因为他的灵活性叫我很方便的操作任何数据

`pom.xml`

```xml
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

### `application.properties`

```properties
mybatis.type-aliases-package=com.haojishu.model

spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

 Spring Boot 会自动加载 `spring.datasource.*` 相关配置 并配置Sql工厂，这些当年在mvc的时候都是需要自己配置的，现在都不用了！

其他的直接看我代码，很简单的，下一次我们讲解如何分页