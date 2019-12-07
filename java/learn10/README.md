

SpringBoot整合Mybatis

`POM.XML`

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!-- mybatisPlus 核心库 -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.0</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
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

`application.properties`

```properties
# 设置提供的服务名
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://192.168.168.10:3306/test?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456a
spring.datasource.max-idle=10
spring.datasource.max-wait=10000
spring.datasource.min-idle=5
spring.datasource.initial-size=5
# mybatis plus
# 指明mapper.xml扫描位置(classpath* 代表编译后类文件根目录)
mybatis-plus.mapper-locations=classpath*:mapper/*.xml
#  指明实体扫描(多个package用逗号或者分号分隔)
mybatis-plus.type-aliases-package=com.haojishu.entity;
#  指定主键生成策略
mybatis-plus.global-config.db-config.id-type=auto
```

配置Mybatis

```java
@Configuration
@MapperScan(basePackages = "com.haojishu.mapper")
public class MybatisPlusConfig {}
```

新建实体类

```java
@Data
@TableName(value = "user")
public class User extends Model {

  private Long Id;

  private String username;

  private String email;
}
```

新建Mapper

```java
@Repository
public interface UserMapper extends BaseMapper<User> {
  List<User> findAllWhereString(String username);
}
```

`UserMapper.xml`

```xml
<mapper namespace="com.haojishu.mapper.UserMapper">
    <resultMap id="BaseResultMap" type="com.haojishu.entity.User">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="username" property="username" jdbcType="VARCHAR"/>
    </resultMap>

    <sql id="baseSql">
        id,username
    </sql>
    <select id="findAllWhereString" resultMap="BaseResultMap">
        select
        <include refid="baseSql"/>
        from
        user
        where username = #{username}
    </select>
</mapper>
```

`新建服务层`

`UserService,java`

```java
public interface UserService extends IService<User> {

  List<User> findAllWhereString(String username);
}
```

服务层实例化

`UserServiceImpl.java`

```java
@Repository
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
  @Override
  public List<User> findAllWhereString(String username) {
    return baseMapper.findAllWhereString(username);
  }
}
```

这个案例完整的应该是演示了Mybatis-plus的使用