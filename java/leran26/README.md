SpringBoot自定义错误处理

我们经常因为各种各样的问题而导致的错误，错误有很多处理的办法，有的是直接使用SpringBoot错误页面路径规定的常见的错误状态码页面路径，一部分是自定义错误处理，我们今天就来讲解下这两种方法

受限我们来模拟一个错误：
```java
@RestController
public class siteController {

    @GetMapping(value = "/")
    public String hello(){
        throw new RuntimeException("错误页面显示");
    }
}
```
> 通过浏览路径，我们发现这么现实是否不好看，那怎么办办呢？我们是不是应该自定义一个错误页面，往下继续看！

1、使用SpringBoot默认的错误页面定义
> src\main\resources\resources\error\500.html

上边路径是SpringBoot默认帮助我们定义好的页面链接，当我们使用；浏览器打开的时候，有错误，会对应调取这个目录的错误页面，大家可以试试

2、自定义错误，这个是我们的重头戏

还是上边代码，刚才我们抛出了RuntimeException，我们自定义错误页面自然而然也应该集成这个错误类，下边看代码！
```java
public class UserCustException extends RuntimeException {
  /** * 错误信息是不是应该包含错误路径，错误信息呢？看代码 */
  private String url;

  private String msg;

  public UserCustException(String url, String msg) {
    super("msg");
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public String getMsg() {
    return msg;
  }
}

```
写完自定义类，但是SpringBoot并不知道你写的是一个什么，我们需要`@ControllerAdvice`来处理异常
```java
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
```
就此，大家可以愉快的使用`UserCustException`了