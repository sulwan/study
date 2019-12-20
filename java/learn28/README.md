SpringBoot自定义拦截器

在讲解拦截器的时候我们还是基于上一章，使用拦截器，当然要了解HandlerInterceptor，我们实现拦截器就是集成HandlerInterceptor,为了讲解清楚这个，我们勇哥比较实际的例子，防止表单重复提交

执行防止重复提交思路，在方法执行前拦截，根据URL当KEY，获取提交数据存储在session，根据设定过期时间判断是否重复提交！

```java
@Component
public class FormInterceptor implements HandlerInterceptor {
  public final String REPEAT_PARAMS = "repeatParams";

  public final String REPEAT_TIME = "repeatTime";

  public final String SESSION_REPEAT_KEY = "repeatData";

  /** 间隔时间，单位:秒 默认10秒 这个间隔时间其实可以放在aop里 */
  private int intervalTime = 10;

  public void setIntervalTime(int intervalTime) {
    this.intervalTime = intervalTime;
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println("执行原本方法前执行");
    System.out.println(handler.getClass().getName());
    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();
      SulwanForm annotation = method.getAnnotation(SulwanForm.class);
      if (annotation != null) {
        if (this.isRepeatSubmit(request)) {
          System.out.println(
              "不允许重复提交，请稍后再试不允许重复提交，请稍后再试不允许重复提交，请稍后再试不允许重复提交，请稍后再试不允许重复提交，请稍后再试不允许重复提交，请稍后再试不允许重复提交，请稍后再试不允许重复提交，请稍后再试不允许重复提交，请稍后再试");
          return false;
        }
      }
      return true;
    }
    return true;
  }

  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable ModelAndView modelAndView)
      throws Exception {
    System.out.println("执行原本方法后执行");
  }

  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      @Nullable Exception ex)
      throws Exception {
    System.out.println("处理拦截之后");
  }

  public boolean isRepeatSubmit(HttpServletRequest request) throws Exception {
    // 本次参数及系统时间
    String nowParams = JSON.marshal(request.getParameterMap());
    Map<String, Object> nowDataMap = new HashMap<String, Object>();
    nowDataMap.put(REPEAT_PARAMS, nowParams);
    nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

    // 请求地址（作为存放session的key值）
    String url = request.getRequestURI();
    HttpSession session = request.getSession();
    Object sessionObj = session.getAttribute(SESSION_REPEAT_KEY);
    if (sessionObj != null) {
      Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
      if (sessionMap.containsKey(url)) {
        Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
        if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
          return true;
        }
      }
    }
    Map<String, Object> sessionMap = new HashMap<String, Object>();
    sessionMap.put(url, nowDataMap);
    session.setAttribute(SESSION_REPEAT_KEY, sessionMap);
    return false;
  }

  /** 判断参数是否相同 */
  private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
    String nowParams = (String) nowMap.get(REPEAT_PARAMS);
    String preParams = (String) preMap.get(REPEAT_PARAMS);
    return nowParams.equals(preParams);
  }

  /** 判断两次间隔时间 */
  private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
    long time1 = (Long) nowMap.get(REPEAT_TIME);
    long time2 = (Long) preMap.get(REPEAT_TIME);
    if ((time1 - time2) < (this.intervalTime * 1000)) {
      return true;
    }
    return false;
  }
}
```

```java
/** 默认三秒不可重复提交 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SulwanForm {}
```

在需要的地方增加@SulwanForm