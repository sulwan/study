综合案例

本项目将是一个综合案例，综合讲解jpa, thymeleaf,使用，为什么我不先讲解thymeleaf，因为我发现上来就讲解语法，那大家和直接看手册有什么区别，那样我觉得我这个教程也就失去了他的意义，做教程是为了别人看和学习，而不是为了做而做。

`pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

`application.properties`

```properties
# h2配置
# 启用SQL语句的日志记录
spring.jpa.show-sql=true
#设置ddl模式
spring.jpa.hibernate.ddl-auto=update
# 数据库连接设置
spring.datasource.url=jdbc:h2:~/test
# 配置h2数据库的连接地址
spring.datasource.username=sa
# 配置数据库用户名
spring.datasource.password=
# 配置数据库密码
spring.datasource.driverClassName=org.h2.Driver
# 配置JDBC Driver
# h2 web console设置
spring.datasource.platform=h2
# 表明使用的数据库平台是h2
spring.h2.console.settings.web-allow-others=true
# 进行该配置后，h2 web consloe就可以在远程访问了。否则只能在本机访问。
spring.h2.console.path=/h2
# 进行该配置，你就可以通过YOUR_URL/h2访问h2 web consloe。YOUR_URL是你程序的访问URl。
spring.h2.console.enabled=true
# 进行该配置，程序开启时就会启动h2 web consloe。当然这是默认的，如果你不想在启动程序时启动h2 web consloe，那么就设置为false。
# 如果你需要在程序启动时对数据库进行初始化操作，则在application.properties文件中对数据库进接配置
#spring.datasource.schema=classpath:db/schema.sql
# 进行该配置后，每次启动程序，程序都会运行resources/db/schema.sql文件，对数据库的结构进行操作。
#spring.datasource.data=classpath:db/data.sql
# 进行该配置后，每次启动程序，程序都会运行resources/db/data.sql文件，对数据库的数据操作。
```

具体看代码吧！其他的看代码，或者之前课程吧！痕迹丹丹的！