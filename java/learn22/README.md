SpringBoot注解切换多数据源

既然使用注解，我们先需要把除了Mybatis，SpringBoot基本组件添加到Maven中之外，我们需要添加注解组件


```xml
    <!-- 其他组件请参考之前文章 -->
    <!-- SpringBoot 拦截器 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
```

1、编写注解

```java
/**
 * 　　取值(ElementType)有：
 *
 * <p>1.CONSTRUCTOR:用于描述构造器 　　　　2.FIELD:用于描述域 　　　　3.LOCAL_VARIABLE:用于描述局部变量 　　　　4.METHOD:用于描述方法
 * 　　　　5.PACKAGE:用于描述包 　　　　6.PARAMETER:用于描述参数 　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 */
@Target({ElementType.METHOD, ElementType.TYPE})

/**
 * 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 * 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 * 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 */
@Retention(RetentionPolicy.RUNTIME)
/** Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。 如果一个类型声明被注释了文档化，它的注释成为公共API的一部分 */
@Documented
public @interface DataSource {
  /** 切换数据源的名称 */
  public DataSourceType value() default DataSourceType.MASTER;
}
```

> 注解的作用就是当我们切换数据源的时候直接在方法明上使用注解即可

2、编写切面切入点

```java
  /**
   * Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是
   * public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为
   * 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
   */
  @Pointcut(
      "@annotation(com.haojishu.annotation.DataSource) || @within(com.haojishu.annotation.DataSource)")
  public void dsPointCut() {}
```

> 编写数据源处理控制

```java
/** 数据源处理 */
public class DynamicDataSourceContextHolder {
  /** 开启日志 */
  public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

  /** 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本， 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。 */
  private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

  /** 设置数据源的变量 */
  public static void setDataSourceType(String dsType) {
    log.info("切换到{}数据源", dsType);
    CONTEXT_HOLDER.set(dsType);
  }

  /** 获得数据源的变量 */
  public static String getDataSourceType() {
    return CONTEXT_HOLDER.get();
  }

  /** 清空数据源变量 */
  public static void clearDataSourceType() {
    CONTEXT_HOLDER.remove();
  }
}

```

> 编写切面逻辑

```java
  /** 获取需要切换的数据源 */
  public DataSource getDataSource(ProceedingJoinPoint point) {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Class<? extends Object> targetClass = point.getTarget().getClass();
    DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
    if (null != targetDataSource) {
      return targetDataSource;
    } else {
      Method method = signature.getMethod();
      DataSource dataSource = method.getAnnotation(DataSource.class);
      return dataSource;
    }
  }

  @Around("dsPointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    DataSource dataSource = getDataSource(point);

    if (null != dataSource) {
      DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
    }

    try {
      return point.proceed();
    } finally {
      // 销毁数据源 在执行方法之后
      DynamicDataSourceContextHolder.clearDataSourceType();
    }
  }
```

3、编写动态数据源

```java
/** 动态数据源 */
public class DynamicDataSource extends AbstractRoutingDataSource {
  public DynamicDataSource(
      DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
    super.setDefaultTargetDataSource(defaultTargetDataSource);
    super.setTargetDataSources(targetDataSources);
    super.afterPropertiesSet();
  }

  @Override
  protected Object determineCurrentLookupKey() {
    return DynamicDataSourceContextHolder.getDataSourceType();
  }
}
```

4、编写数据源配置类

```java
/** 这是一个配置文件 */
@Configuration
public class MybatisConfig {

  /**
   * 设置主数据库
   *
   * @return
   */
  @Bean
  @ConfigurationProperties("spring.datasource.druid.master")
  public DataSource masterDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * 设置从数据库
   *
   * @return
   */
  @Bean
  @ConfigurationProperties("spring.datasource.druid.slave")
  @ConditionalOnProperty(
      prefix = "spring.datasource.druid.slave",
      name = "enabled",
      havingValue = "true")
  public DataSource slaveDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "dynamicDataSource")
  @Primary
  public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
    targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
    return new DynamicDataSource(masterDataSource, targetDataSources);
  }
}
```

到这数据库多数据源也就写完

