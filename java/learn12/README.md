SpringBoot整合swagger2

`POM.XML`

```xml
        <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```

`配置swagger2`

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  /** 创建API */
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
        .apiInfo(apiInfo())
        // 设置哪些接口暴露给Swagger展示
        .select()
        // 扫描所有有注解的api，用这种方式更灵活
        //        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        // 扫描指定包中的swagger注解
        // .apis(RequestHandlerSelectors.basePackage("com.haojishu.controller"))
        // 扫描所有 .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  /** 添加摘要信息 */
  private ApiInfo apiInfo() {
    // 用ApiInfoBuilder进行定制
    return new ApiInfoBuilder()
        // 设置标题
        .title("标题：API接口文档演示")
        // 描述
        .description("描述：这就是方便查看API的文档")
        // 作者信息
        .contact(new Contact("心肝宝贝", null, null))
        // 版本
        .version("版本号:" + "1.0")
        .build();
  }
}
```



1、@Api：用在请求的类上，说明该类的作用
   tags="说明该类的作用"
   value="该参数没什么意义，所以不需要配置"
示例：

```
@Api(tags="APP用户注册Controller")
```

2、@ApiOperation：用在请求的方法上，说明方法的作用
@ApiOperation："用在请求的方法上，说明方法的作用"
  value="说明方法的作用"
  notes="方法的备注说明"
示例：

```
@ApiOperation(value="用户注册",notes="手机号、密码都是必输项，年龄随边填，但必须是数字")
```

3、@ApiImplicitParams：用在请求的方法上，包含一组参数说明
   @ApiImplicitParams：用在请求的方法上，包含一组参数说明
   @ApiImplicitParam：用在 @ApiImplicitParams 注解中，指定一个请求参数的配置信息    
    name：参数名
    value：参数的汉字说明、解释
    required：参数是否必须传
    paramType：参数放在哪个地方
      · header --> 请求参数的获取：@RequestHeader
      · query --> 请求参数的获取：@RequestParam
      · path（用于restful接口）--> 请求参数的获取：@PathVariable
      · body（不常用）
      · form（不常用）   
    dataType：参数类型，默认String，其它值dataType="Integer"    
    defaultValue：参数的默认值

示列：

```java
@ApiImplicitParams({
    @ApiImplicitParam(name="mobile",value="手机号",required=true,paramType="form"),
    @ApiImplicitParam(name="password",value="密码",required=true,paramType="form"),
    @ApiImplicitParam(name="age",value="年 龄",required=true,paramType="form",dataType="Integer")
})
```

4、@ApiResponses：用于请求的方法上，表示一组响应
   @ApiResponses：用于请求的方法上，表示一组响应
   @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
    code：数字，例如400
    message：信息，例如"请求参数没填好"
    response：抛出异常的类
示例：

```java
@ApiOperation(value = "select1请求",notes = "多个参数，多种的查询参数类型")
@ApiResponses({
    @ApiResponse(code=400,message="请求参数没填好"),
    @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
})
```


5、@ApiModel：用于响应类上，表示一个返回响应数据的信息
   @ApiModel：用于响应类上，表示一个返回响应数据的信息
      （这种一般用在post创建的时候，使用@RequestBody这样的场景，
      请求参数无法使用@ApiImplicitParam注解进行描述的时候）
   @ApiModelProperty：用在属性上，描述响应类的属性


示例:

```java
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
@ApiModel(description= "返回响应数据")
public class RestMessage implements Serializable{
    @ApiModelProperty(value = "是否成功")
    private boolean success=true;
    @ApiModelProperty(value = "返回对象")
    private Object data;
    @ApiModelProperty(value = "错误编号")
    private Integer errCode;
    @ApiModelProperty(value = "错误信息")
    private String message;
    /* getter/setter */
}
```

 

`实例`

```java
@RestController
@Api("演示文档标注")
public class siteController {

  @ApiOperation("返回Hello字符串")
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String hello() {
    return "hello";
  }

  @ApiOperation("返回用户信息")
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  @ApiImplicitParam(name = "User", value = "用户信息", dataType = "User")
  public User helloUser(User user) {
    return user;
  }
}
```

