package com.haojishu.Filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Date;

@Component
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
