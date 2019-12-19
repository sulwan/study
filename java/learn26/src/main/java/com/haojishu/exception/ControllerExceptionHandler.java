package com.haojishu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
  /** 指定异常使用那个类处理 */
  @ExceptionHandler(UserCustException.class)
  /** 表示该方法的返回结果直接写入 HTTP response body 中 */
  @ResponseBody
  /** 这里作用就是改变服务器响应的状态码 ,比如一个本是200的请求可以通过@ResponseStatus 改成404 */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleUserNotExistsException(UserCustException e) {
    Map<String, Object> map = new HashMap<>();
    map.put("url", e.getUrl());
    map.put("message", e.getMsg());
    return map;
  }
}
