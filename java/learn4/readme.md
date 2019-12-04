

SpringBoot读取properties



读取properties文件有两种情况：
			一种是读取application.properties
			一种是读取自定义文件例如：sulwan.properties



> 读取第一种情况不需要加载文件，默认文件已经被加载
>
> 第二种情况需要我们自行加载文件
>
> ```java
> @PropertySource(
>     value = "classpath:sulwan.properties",
>     ignoreResourceNotFound = false,
>     encoding = "UTF-8")
> ```

配置文件变量的读取

> ```java
> //使用@Value赋值；
> //1、基本数值
> //2、可以写SpEL； #{}
> //3、可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）
>     @Value("1")
>     private Integer id;
>     @Value("${person.nickName}")
>     private String name;
>     @Value("#{20-2}")
>     private String age;
> ```

完整代码

sulwan.properties

```properties
sulwan=这是一个变量
```



```java
package com.haojishu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource(
    value = "classpath:sulwan.properties",
    ignoreResourceNotFound = false,
    encoding = "UTF-8")
public class siteController {

  @Value("${sulwan}")
  private String username;

  @RequestMapping(value = "/")
  public String hello() {
    System.out.println(username);
    return "hello properties";
  }
}
```

