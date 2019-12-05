package com.haojishu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LogAspect {

  @Pointcut("execution(public * com.haojishu.controller.*.*(..))")
  public void log() {}

  // 切入点之前执行
  @Before("log()")
  public void doBefore(JoinPoint jpt) throws Throwable {
    System.out.println("--------doBefore--------start");
    String methodName = jpt.getSignature().getName();
    List<Object> args = Arrays.asList(jpt.getArgs());
    System.out.println("调用前连接点方法为：" + methodName + ",参数为：" + args);
    System.out.println("--------doBefore--------end");
  }

  // return之后执行
  @AfterReturning(returning = "ret", pointcut = "log()")
  public void doAfterReturning(Object ret) throws Throwable {
    System.out.println("doAfterReturning : " + ret);
  }

  // 切入点之后执行
  @After("log()")
  public void doAfter(JoinPoint jpt) throws Throwable {
    System.out.println("--------doAfter--------start");
    String methodName = jpt.getSignature().getName();
    List<Object> args = Arrays.asList(jpt.getArgs());
    System.out.println("调用后连接点方法为：" + methodName + ",参数为：" + args);
    System.out.println("--------doAfter--------end");
  }

  @Around("log()")
  public Object aroundMethod(ProceedingJoinPoint pdj) {
    /*result为连接点的放回结果*/
    Object result = null;
    String methodName = pdj.getSignature().getName();

    /*前置通知方法*/
    System.out.println("<前置通知方法>目标方法名：" + methodName + ",参数为：" + Arrays.asList(pdj.getArgs()));

    /*执行目标方法*/
    try {
      result = pdj.proceed();

      /*返回通知方法*/
      System.out.println("<返回通知方法>目标方法名" + methodName + ",返回结果为：" + result);
    } catch (Throwable e) {
      /*异常通知方法*/
      System.out.println("<异常通知方法>目标方法名" + methodName + ",异常为：" + e);
    }

    /*后置通知*/
    System.out.println("后置通知方法>目标方法名" + methodName);

    return result;
  }
}
