SpringBoot计划任务很简单简单的我都不知道怎么写了看代码

`pom.xml`

```xml
      <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

其实这些就是基本配置，一个简化插件可有可无，一个测试插件，一个热部署插件，其实主要使用的就第一个插件！

```java
// 开启计划任务
@EnableScheduling
// 告诉SpringBoot加载上
@Component
public class Hello {

  // 这个说明计划任务啥时候执行
  @Scheduled(fixedRate = 1000)
  public void site() {
    System.out.println("sulwan");
  }
}

```

fixedDelay非常好理解，它的间隔时间是根据上次的任务结束的时候开始计时的。比如一个方法上设置了fixedDelay=5*1000，那么当该方法某一次执行结束后，开始计算时间，当时间达到5秒，就开始再次执行该方法。

fixedRate理解起来比较麻烦，它的间隔时间是根据上次任务开始的时候计时的。比如当方法上设置了fiexdRate=5*1000，该执行该方法所花的时间是2秒，那么3秒后就会再次执行该方法。
但是这里有个坑，当任务执行时长超过设置的间隔时长，那会是什么结果呢。打个比方，比如一个任务本来只需要花2秒就能执行完成，我所设置的fixedRate=5*1000，但是因为网络问题导致这个任务花了7秒才执行完成。当任务开始时Spring就会给这个任务计时，5秒钟时候Spring就会再次调用这个任务，可是发现原来的任务还在执行，这个时候第二个任务就阻塞了（这里只考虑单线程的情况下，多线程后面再讲），甚至如果第一个任务花费的时间过长，还可能会使第三第四个任务被阻塞。被阻塞的任务就像排队的人一样，一旦前一个任务没了，它就立马执行。