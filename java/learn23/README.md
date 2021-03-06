 Spring Boot整合Ehcache

> 之前的教程我在写的时候主要是为了省事没有分离服务层，当我们正常工作的时候，其实还有一个服务层具体如下：`UsersService` `UsersServiceImpl`
>
> 这次我加上了服务层的隔离，并不是直接去使用mapper

**注释介绍**

**@Cacheable**

@Cacheable 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存

@Cacheable 作用和配置方法



| 参数      | 解释                                                         | example                                                      |
| :-------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| value     | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个       | 例如: @Cacheable(value=”mycache”) @Cacheable(value={”cache1”,”cache2”} |
| key       | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 | @Cacheable(value=”testcache”,key=”#userName”)                |
| condition | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存 | @Cacheable(value=”testcache”,condition=”#userName.length()>2”) |



**实例**

@Cacheable(value=”accountCache”)，这个注释的意思是，当调用这个方法的时候，会从一个名叫 accountCache 的缓存中查询，如果没有，则执行实际的方法（即查询数据库），并将执行的结果存入缓存中，否则返回缓存中的对象。这里的缓存中的 key 就是参数 userName，value 就是 Account 对象。“accountCache”缓存是在 spring*.xml 中定义的名称。

```java
@Cacheable(value="accountCache")// 使用了一个缓存名叫 accountCache 
public Account getAccountByName(String userName) {
   // 方法内部实现不考虑缓存逻辑，直接实现业务
   System.out.println("real query account."+userName); 
   return getFromDB(userName); 
}
```

**@CachePut**

@CachePut 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用

@CachePut 作用和配置方法



| 参数      | 解释                                                         | example                                                      |
| :-------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| value     | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个       | @CachePut(value=”my cache”)                                  |
| key       | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 | @CachePut(value=”testcache”,key=”#userName”)                 |
| condition | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存 | @CachePut(value=”testcache”,condition=”#userName.length()>2”) |



实例

@CachePut 注释，这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中，实现缓存与数据库的同步更新。

```java
@CachePut(value="accountCache",key="#account.getName()")// 更新accountCache 缓存
public Account updateAccount(Account account) { 
  return updateDB(account); 
}
```

**@CacheEvict**

@CachEvict 的作用 主要针对方法配置，能够根据一定的条件对缓存进行清空

@CacheEvict 作用和配置方法



| 参数             | 解释                                                         | example                                                      |
| :--------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| value            | 缓存的名称，在 spring 配置文件中定义，必须指定至少一个       | @CacheEvict(value=”my cache”)                                |
| key              | 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合 | @CacheEvict(value=”testcache”,key=”#userName”)               |
| condition        | 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存 | @CacheEvict(value=”testcache”,condition=”#userName.length()>2”) |
| allEntries       | 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存 | @CachEvict(value=”testcache”,allEntries=true)                |
| beforeInvocation | 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存 | @CachEvict(value=”testcache”，beforeInvocation=true)         |



实例

```java
@CacheEvict(value="accountCache",key="#account.getName()")// 清空accountCache 缓存 
public void updateAccount(Account account) {
   updateDB(account); 
} 
 
@CacheEvict(value="accountCache",allEntries=true)// 清空accountCache 缓存
public void reload() {
   reloadAll()
}
 
@Cacheable(value="accountCache",condition="#userName.length() <=4")// 缓存名叫 accountCache 
public Account getAccountByName(String userName) { 
 // 方法内部实现不考虑缓存逻辑，直接实现业务
 return getFromDB(userName); 
}
```

**@CacheConfig**

所有的@Cacheable（）里面都有一个value＝“xxx”的属性，这显然如果方法多了，写起来也是挺累的，如果可以一次性声明完 那就省事了， 所以，有了@CacheConfig这个配置，@CacheConfig is a class-level annotation that allows to share the cache names，如果你在你的方法写别的名字，那么依然以方法的名字为准。

```java
@CacheConfig("books")
public class BookRepositoryImpl implements BookRepository {
 
  @Cacheable
  public Book findBook(ISBN isbn) {...}
}
```

**条件缓存**

下面提供一些常用的条件缓存

```java
//@Cacheable将在执行方法之前( #result还拿不到返回值)判断condition，如果返回true，则查缓存； 
@Cacheable(value = "user", key = "#id", condition = "#id lt 10")
public User conditionFindById(final Long id) 
 
//@CachePut将在执行完方法后（#result就能拿到返回值了）判断condition，如果返回true，则放入缓存； 
@CachePut(value = "user", key = "#id", condition = "#result.username ne 'zhang'") 
public User conditionSave(final User user)  
 
//@CachePut将在执行完方法后（#result就能拿到返回值了）判断unless，如果返回false，则放入缓存；（即跟condition相反）
@CachePut(value = "user", key = "#user.id", unless = "#result.username eq 'zhang'")
public User conditionSave2(final User user)  
 
//@CacheEvict， beforeInvocation=false表示在方法执行之后调用（#result能拿到返回值了）；且判断condition，如果返回true，则移除缓存；
@CacheEvict(value = "user", key = "#user.id", beforeInvocation = false, condition = "#result.username ne 'zhang'") 
public User conditionDelete(final User user)  

```

**@Caching**

有时候我们可能组合多个Cache注解使用；比如用户新增成功后，我们要添加id–>user；username—>user；email—>user的缓存；此时就需要@Caching组合多个注解标签了。

```java
@Caching(put = {
@CachePut(value = "user", key = "#user.id"),
@CachePut(value = "user", key = "#user.username"),
@CachePut(value = "user", key = "#user.email")
})
public User save(User user) {
```

**自定义缓存注解**

比如之前的那个@Caching组合，会让方法上的注解显得整个代码比较乱，此时可以使用自定义注解把这些注解组合到一个注解中，如：

```java
@Caching(put = {
@CachePut(value = "user", key = "#user.id"),
@CachePut(value = "user", key = "#user.username"),
@CachePut(value = "user", key = "#user.email")
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserSaveCache {
}
```

这样我们在方法上使用如下代码即可，整个代码显得比较干净。

```
@UserSaveCache``public` `User save(User user)
```

扩展

比如findByUsername时，不应该只放username–>user，应该连同id—>user和email—>user一起放入；这样下次如果按照id查找直接从缓存中就命中了

```java
@Caching(
  cacheable = {
    @Cacheable(value = "user", key = "#username")
  },
  put = {
    @CachePut(value = "user", key = "#result.id", condition = "#result != null"),
    @CachePut(value = "user", key = "#result.email", condition = "#result != null")
  }
)
public User findByUsername(final String username) {
  System.out.println("cache miss, invoke find by username, username:" + username);
  for (User user : users) {
    if (user.getUsername().equals(username)) {
      return user;
    }
  }
  return null;
}
```

其实对于：id—>user；username—->user；email—>user；更好的方式可能是：id—>user；username—>id；email—>id；保证user只存一份；如：

```java
@CachePut(value="cacheName", key="#user.username", cacheValue="#user.username") 
public void save(User user)  
 
 
@Cacheable(value="cacheName", key="#user.username", cacheValue="#caches[0].get(#caches[0].get(#username).get())") 
public User findByUsername(String username) 
```

SpEL上下文数据

Spring Cache提供了一些供我们使用的SpEL上下文数据，下表直接摘自Spring官方文档：



| 名称          | 位置       | 描述                                                         | 示例                |
| :------------ | :--------- | :----------------------------------------------------------- | :------------------ |
| methodName    | root对象   | 当前被调用的方法名                                           | root.methodName     |
| method        | root对象   | 当前被调用的方法                                             | root.method.name    |
| target        | root对象   | 当前被调用的目标对象                                         | root.target         |
| targetClass   | root对象   | 当前被调用的目标对象类                                       | root.targetClass    |
| args          | root对象   | 当前被调用的方法的参数列表                                   | root.args[0]        |
| caches        | root对象   | 当前方法调用使用的缓存列表（如@Cacheable(value={“cache1”, “cache2”})），则有两个cache | root.caches[0].name |
| argument name | 执行上下文 | 当前被调用的方法的参数，如findById(Long id)，我们可以通过#id拿到参数 | user.id             |
| result        | 执行上下文 | 方法执行后的返回值（仅当方法执行之后的判断有效，如‘unless'，'cache evict'的beforeInvocation=false） | result              |

```java
@CacheEvict``(value = ``"user"``, key = ``"#user.id"``, condition = ``"#root.target.canCache() and #root.caches[0].get(#user.id).get().username ne #user.username"``, beforeInvocation = ``true``) ``public` `void` `conditionUpdate(User user)
```

现在我们开始来测试吧！
`pom.xml`
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <!-- spring-boot redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
```

`application.properties`

```properties
mybatis.config-location=classpath:mybatis/mybatis-config.xml
# 指定mybatis配置XML文件
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
# 指定Mapper文件路径
mybatis.type-aliases-package=com.haojishu.mapper
# 指定实体路径
spring.datasource.url=jdbc:mysql://192.168.168.10:3306/demo?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
# 指定数据源连接地址
spring.datasource.username=root
# 数据连接地址
spring.datasource.password=123456a
# 数据库连接密码
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 设置连接类
###显示SQL语句部分
log4j.logger.com.ibatis=DEBUG
log4j.logger.com.ibatis.common.jdbc.SimpleDataSource=DEBUG
log4j.logger.com.ibatis.common.jdbc.ScriptRunner=DEBUG
log4j.logger.com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate=DEBUG
log4j.logger.Java.sql.Connection=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG

#
# Lettuce 版本
#
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=192.168.168.10
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=123456a
# 连接池最大连接数（使用负值表示没有限制） 默认 8
spring.redis.lettuce.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
spring.redis.lettuce.pool.max-wait=-1
# 连接池中的最大空闲连接 默认 8
spring.redis.lettuce.pool.max-idle=8
# 连接池中的最小空闲连接 默认 0
spring.redis.lettuce.pool.min-idle=0
```

> 开启缓存注解

`RedisConfig`

```java
@Configuration //指明这是一个配置文件
@EnableCaching //启用注解缓存
public class RedisConfig extends CachingConfigurerSupport {
  @Bean
  public KeyGenerator keyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
        StringBuffer sb = new StringBuffer();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
          sb.append(obj.toString());
        }
        System.out.println("调用Redis生成key：" + sb.toString());
        return sb.toString();
      }
    };
  }
}
```

这些就是关键代码了，大家在测试案例的时候重点关注list与list2的区别哦