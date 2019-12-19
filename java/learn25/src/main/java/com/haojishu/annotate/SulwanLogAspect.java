package com.haojishu.annotate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class SulwanLogAspect {
  /** 定义切入点 */
  @Pointcut(
      "@annotation(com.haojishu.annotate.SulwanLog) || @within(com.haojishu.annotate.SulwanLog)")
  public void pointCut() {}

  @Around("pointCut()")
  public Object around(ProceedingJoinPoint point) {
    Object result = null;
    long beginTime = System.currentTimeMillis();
    try {
      // 执行方法
      result = point.proceed();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    /** 这里记录你的日志 */
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    // 注解描述
    SulwanLog logAnnotation = method.getAnnotation(SulwanLog.class);
    System.out.println("注解描述：" + logAnnotation.value());
    // 请求的方法名
    String className = point.getTarget().getClass().getName();
    System.out.println("方法名称：" + className);
    String methodName = signature.getName();
    System.out.println("签名名称：" + methodName);
    // 请求方法参数值
    Object[] args = point.getArgs();
    // 请求方法参数
    LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
    String[] paramNames = u.getParameterNames(method);
    if (args != null && paramNames != null) {
      String params = "";
      for (int i = 0; i < args.length; i++) {
        params += "  " + paramNames[i] + ": " + args[i];
      }
      System.out.println(params);
    }
    return result;
  }
}
