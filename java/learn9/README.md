MyBatis xml教程

首先配置`POM.XML`

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.1</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

配置`appliction.properties`

```properties
mybatis.config-location=classpath:mybatis/mybatis-config.xml
# 指定mybatis配置XML文件
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
# 指定Mapper文件路径
mybatis.type-aliases-package=com.haojishu.mapper
# 指定实体路径
spring.datasource.url=jdbc:mysql://192.168.168.10:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
# 指定数据源连接地址
spring.datasource.username=root
# 数据连接地址
spring.datasource.password=123456a
# 数据库连接密码
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 设置连接类
```

增加`Mybatis`配置文件

```java
@Configuration
@MapperScan("com.haojishu.mapper")
public class mybatis {}
```

创建Mybatis XML 类型配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>
```



SpringBoot整合Mybatis到这里就完成了，现在我们演示下怎么CRUD

创建实体类

```java
public class UserEntity {

	private Long Id;

	private String username;
  
  ...... 此处省略Get Set
}
```

创建Mapper接口

```java
public interface UserMapper {

	List<UserEntity> getAll();

	UserEntity getOne(Long id);

	void insert(UserEntity userEntity);

	void update(UserEntity userEntity);

	void delete(Long id);
}
```

创建Mapper Xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.haojishu.mapper.UserMapper">
    <resultMap id="BaseResultMap" type="com.haojishu.entity.UserEntity">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="username" property="username" jdbcType="VARCHAR"/>
    </resultMap>

    <sql id="baseSql">
        id,username
    </sql>

    <select id="getAll" resultMap="BaseResultMap">
        select
        <include refid="baseSql"/>
        from
        user
    </select>

    <select id="getOne" parameterType="Long" resultMap="BaseResultMap">
        select
        <include refid="baseSql"/>
        from user
        where id = #{id}
    </select>

    <insert id="insert" parameterType="com.haojishu.entity.UserEntity">
        insert into user (username) values (#{username})
    </insert>

    <update id="update" parameterType="com.haojishu.entity.UserEntity">
        update user set
        <if test="username != null">username = #{username}</if>
        where
        id = #{id}
    </update>

    <delete id="delete" parameterType="Long">
        delete from user where id = #{id}
    </delete>
</mapper>
```

使用siteController

```java
@RestController
public class siteController {

  @Autowired UserMapper userMapper;

  // 查询所有数据
  @RequestMapping(value = "/")
  public String hello() {
    List<UserEntity> list = userMapper.getAll();
    System.out.println(list);
    return "hello";
  }

  // 根据条件Id查询
  @RequestMapping(value = "/getOne")
  public UserEntity getOne() {
    UserEntity userEntity = userMapper.getOne(4L);
    return userEntity;
  }

  // 根据条件Id删除
  @RequestMapping(value = "/del")
  public String del() {
    userMapper.getOne(1L);
    return "del";
  }

  // 根据条件更新
  public String update() {
    UserEntity userEntity = new UserEntity();
    userMapper.update(userEntity);
    return "update";
  }
}
```

