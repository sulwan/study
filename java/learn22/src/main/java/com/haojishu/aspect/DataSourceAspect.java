package com.haojishu.aspect;

import com.haojishu.annotation.DataSource;
import com.haojishu.config.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/** @Aspect:作用是把当前类标识为一个切面供容器读取 */
@Aspect
/** @controller 控制器（注入服务） */
@Component
@Order(1)
public class DataSourceAspect {
  /** 开启日志记录 */
  private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

  /**
   * Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是
   * public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为
   * 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
   */
  @Pointcut(
      "@annotation(com.haojishu.annotation.DataSource) || @within(com.haojishu.annotation.DataSource)")
  public void dsPointCut() {}

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
}
