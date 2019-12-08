SpringBoot整合开发热部署

在开发的时候是不是受够了改一点，从起一次服务呢？反正我是收购了，使用这个插件，天空一片美好

`pom.xml`

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
```

Intellij IDEA中默认是关闭了自动编译的，可以按照如下2步设置开启：
1、IDEA开启项目自动编译，进入设置(ctrl+alt+s)—Build,Execution,Deployment> Compiler 勾选中左侧的Build Project automatically。

2、IDEA开启项目运行时自动make, ctrl + shift + alt+/ 命令：registry -> 勾选。