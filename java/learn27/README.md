SpringBoot自定义过滤器



自定义过滤器有很多做用，比如我们希望那个权限页面被控制，那个页面不需要控制，都是用到过滤器的，更比如我们之前在讲解JSON的时候，格式化时间，那个时候其实就可以用过滤器初始化

```java
@Component
/** 指定这个是个过滤器并设置过滤规则 */
@WebFilter(urlPatterns = {"/*"})
public class CustFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("初始化过滤器");
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    System.out.println("开始执行过滤器");
    Long start = new Date().getTime();
    /** 执行原步骤 */
    filterChain.doFilter(servletRequest, servletResponse);
    System.out.println("【过滤器】耗时 " + (new Date().getTime() - start));
    System.out.println("结束执行过滤器");
  }

  @Override
  public void destroy() {
    System.out.println("过滤器销毁");
  }
}
```

