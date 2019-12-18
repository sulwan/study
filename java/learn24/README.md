Spring自带JSon解析

> 百分之80或者更多的人使用的都是阿里的JSON解析库，为啥我不知道，我有的时候也用，这章讲解的SpringBoot自带的Json库，当然阿里的以后我也会说说，毕竟多一种选择吧！

`pom.xml`

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
```

我们经常这么使用：

```java
@RestController
public class siteController {

    @GetMapping(value = "/")
    public Users hello(){
        Users users = new Users();
        users.setUsername("sulwan");
        users.setId(2);
    	users.setBirthday(new Date());
        return users;
    }
}
```
> 这也算一种Json的解析形式吧！

他是怎么实现的呢，Jackson是使用ObjectMapper 对象来实现的，不信我们来在看一段代码

```java
@RestController
public class siteController {

  @Autowired ObjectMapper objectMapper;

  @GetMapping(value = "/")
  public Users hello() throws JsonProcessingException {
    Users users = new Users();
    users.setUsername("sulwan");
    users.setId(2);
    users.setBirthday(new Date());

    System.out.println(objectMapper.writeValueAsString(users));
    return users;
  }
}
```
那问题来了我们可以不可以更改下时间的显示方式呢，上边两种方式其实是一种实现，只是我们把内部方法写出来了，我们既然要修改，肯定就是要设置ObjectMapper，那么应该先声明，在使用撒！看代码！
```java
@Configuration
public class JackSonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    return objectMapper;
  }
}
```
在试试刚才输出是不是完美了，哈哈，其实顺便学习了下@Bean是干啥的，不太清楚的就先这个么用，我先带大家入门，最后我还是会有专门章节在讲解基础语法和Spring的。

我们在看一个属性，有的时候我们是不是查询账户用户信息的时候有账户密码，这个时候有的人是直接新建一个实体类，其实不用，咱们可以利用Jackson的注解来实现，看下代码
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

  private static final long serialVersionUID = -4380431940533975714L;

  private Integer id;

  private String username;

  @JsonIgnore private Date birthday;
}

```
在运行下，是不是生日没有了，其实就是这么简单，下边是常用的注解，大家自己看吧！

## 反序列化

使用`@ResponseBody`注解可以使对象序列化为JSON格式字符串，除此之外，Jackson也提供了反序列化方法。

### 树遍历

当采用树遍历的方式时，JSON被读入到JsonNode对象中，可以像操作XML DOM那样读取JSON。比如：

```java
  @GetMapping(value = "/readJson")
  public String readJson() throws JsonProcessingException {
    String json = "{\"id\":1,\"username\":\"sulwan\"}";
    JsonNode node = objectMapper.readTree(json);
    System.out.println(node);
    String username = node.get("username").toString();
    Integer id = node.get("id").intValue();
    return username + "  " + id;
  }
```



`readTree`方法可以接受一个字符串或者字节数组、文件、InputStream等， 返回JsonNode作为根节点，你可以像操作XML DOM那样操作遍历JsonNode以获取数据。

解析多级JSON例子：

```java
  /** 演示反序列化多级 */
  @GetMapping(value = "/readJsonMu")
  public String readJsonMut() throws JsonProcessingException {
    String json = "{\"id\":1,\"info\":{\"addr\":\"hebei\",\"city\":\"石家庄\"}}";
    JsonNode node = objectMapper.readTree(json);
    JsonNode info = node.get("info");
    System.out.println(info);
    String addr = info.get("addr").asText();
    return addr;
  }
```



### 绑定对象

 在日常的时候我们更多的是把json扔给一个我们的实体对象

```java
  /** 把Json解析给实体类 */
  @GetMapping(value = "/readJsonEntity")
  public String readJsonEntity() throws JsonProcessingException {

    String json = "{\"id\":1,\"info\":{\"addr\":\"hebei\",\"city\":\"石家庄\"}}";
    Users users = objectMapper.readValue(json, Users.class);

    String addr = users.getInfo().getAddr();
    return addr;
  }
```



## Jackson注解

Jackson包含了一些实用的注解：

### @JsonProperty

`@JsonProperty`，作用在属性上，用来为JSON Key指定一个别名。

```json
  @JsonProperty("infos")
  private Info info;
```



```json
{
    "id": 2,
    "username": "sulwan",
    "infos": null
}
```



key birthday已经被替换为了bth。

### @Jsonlgnore

`@Jsonlgnore`，作用在属性上，用来忽略此属性。

```java
  @JsonIgnore private Date birthday;
```



再次访问`getuser`页面输出：

```json
{
    "id": 2,
    "username": "sulwan",
    "info": null
}
```



birthday属性已被忽略。

### @JsonIgnoreProperties

`@JsonIgnoreProperties`，忽略一组属性，作用于类上，比如`JsonIgnoreProperties({ "password", "age" })`。

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"age"})
public class Users implements Serializable {

  private static final long serialVersionUID = -4380431940533975714L;

  private Integer id;

  private String username;

  private Integer age;

  @JsonIgnore private Date birthday;

  @JsonProperty("infos")
  private Info info;
}
```



再次访问`getuser`页面输出：

```json
{
    "id": 2,
    "username": "sulwan",
    "infos": null
}
```



### @JsonFormat

`@JsonFormat`，用于日期格式化，如：

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")private Date birthday;
```

### @JsonSerialize

`@JsonSerialize`，指定一个实现类来自定义序列化。类必须实现`JsonSerializer`接口，代码如下：

```java
public class UserSerializer extends JsonSerializer<User> {

    @Override
    public void serialize(User user, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("user-name", user.getUserName());
        generator.writeEndObject();
    }
}
```



上面的代码中我们仅仅序列化userName属性，且输出的key是`user-name`。 使用注解`@JsonSerialize`来指定User对象的序列化方式：

```java
@JsonSerialize(using = UserSerializer.class)
public class User implements Serializable {
    ...
}
```



再次访问`getuser`页面输出：

```json
{"user-name":"sulwan"}
```



### @JsonDeserialize

`@JsonDeserialize`，用户自定义反序列化，同`@JsonSerialize` ，类需要实现`JsonDeserializer`接口。

```java
public class UserDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        String userName = node.get("username").asText();
        User user = new User();
        user.setUserName(userName);
        return user;
    }
}
```



使用注解`@JsonDeserialize`来指定User对象的序列化方式：

```
@JsonDeserialize (using = UserDeserializer.class)public class User implements Serializable {    ...}
```



测试：

```java
@Autowired
ObjectMapper mapper;

@RequestMapping("readjsonasobject")
@ResponseBody
public String readJsonAsObject() {
    try {
        String json = "{\"username\":\"sulwan\"}";
        User user = mapper.readValue(json, User.class);
        String name = user.getUserName();
        return name;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
```



访问`readjsonasobject`，页面输出：

```
sulwan
```



### @JsonView

`@JsonView`，作用在类或者属性上，用来定义一个序列化组。 比如对于User对象，某些情况下只返回userName属性就行，而某些情况下需要返回全部属性。 因此User对象可以定义成如下：

```java
public class User implements Serializable {
    private static final long serialVersionUID = 6222176558369919436L;
    
    public interface UserNameView {};
    public interface AllUserFieldView extends UserNameView {};
    
    @JsonView(UserNameView.class)
    private String userName;
    
    @JsonView(AllUserFieldView.class)
    private int age;
    
    @JsonView(AllUserFieldView.class)
    private String password;
    
    @JsonView(AllUserFieldView.class)
    private Date birthday;
    ...	
}
```



User定义了两个接口类，一个为`userNameView`，另外一个为`AllUserFieldView`继承了`userNameView`接口。这两个接口代表了两个序列化组的名称。属性userName使用了`@JsonView(UserNameView.class)`，而剩下属性使用了`@JsonView(AllUserFieldView.class)`。

Spring中Controller方法允许使用`@JsonView`指定一个组名，被序列化的对象只有在这个组的属性才会被序列化，代码如下：

```java
@JsonView(User.UserNameView.class)
@RequestMapping("/")
@ResponseBody
public User getUser() {
    User user = new User();
    user.setUserName("sulwan");
    user.setAge(26);
    user.setPassword("123456");
    user.setBirthday(new Date());
    return user;
}
```



访问`getuser`页面输出：

```json
{"userName":"mrbird"}
```



如果将`@JsonView(User.UserNameView.class)`替换为`@JsonView(User.AllUserFieldView.class)`，输出：

```
{"userName":"sulwan","age":26,"password":"123456","birthday":"2018-04-02 11:24:00"}
```



因为接口`AllUserFieldView`继承了接口`UserNameView`所以userName也会被输出。

## 集合的反序列化

在Controller方法中，可以使用`＠RequestBody`将提交的JSON自动映射到方法参数上，比如：

```java
@RequestMapping("updateuser")
@ResponseBody
public int updateUser(@RequestBody List<User> list){
	return list.size();
}
```



上面方法可以接受如下一个JSON请求，并自动映射到User对象上：

```
[{"userName":"sulwan","age":26},{"userName":"scott","age":27}]
```



Spring Boot 能自动识别出List对象包含的是User类，因为在方法中定义的泛型的类型会被保留在字节码中，所以Spring Boot能识别List包含的泛型类型从而能正确反序列化。

有些情况下，集合对象并没有包含泛型定义，如下代码所示，反序列化并不能得到期望的结果。

```java
@Autowired
ObjectMapper mapper;

@RequestMapping("customize")
@ResponseBody
public String customize() throws JsonParseException, JsonMappingException, IOException {
    String jsonStr = "[{\"username\":\"sulwan\",\"age\":26},{\"username\":\"haha\",\"age\":27}]";
    List<User> list = mapper.readValue(jsonStr, List.class);
    String msg = "";
    for (User user : list) {
        msg += user.getUserName();
    }
    return msg;
}
```



访问`customize`，控制台抛出异常：

```
java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to com.example.pojo.User
```



这是因为在运行时刻，泛型己经被擦除了（不同于方法参数定义的泛型，不会被擦除）。为了提供泛型信息，Jackson提供了JavaType ，用来指明集合类型，将上述方法改为：

```java
@Autowired
ObjectMapper mapper;

@RequestMapping("customize")
@ResponseBody
public String customize() throws JsonParseException, JsonMappingException, IOException {
    String jsonStr = "[{\"username\":\"sulwan\",\"age\":26},{\"username\":\"haha\",\"age\":27}]";
    JavaType type = mapper.getTypeFactory().constructParametricType(List.class, User.class);
    List<User> list = mapper.readValue(jsonStr, type);
    String msg = "";
    for (User user : list) {
        msg += user.getUserName();
    }
    return msg;
}
```



访问`customize`，页面输出：`mrbirdscott`。

