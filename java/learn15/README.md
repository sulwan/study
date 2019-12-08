#### SpringBoot junit 测试 controller (MockMvc)

基于 RESTful风格的 Spring MVC的测试,我们可以测试完整的 Spring MVC流程

##### 一 .  MockMvcBuilder

MockMvcBuilder 是用来构造MockMvc的构造器,其主要有两个实现 : StandaloneMockMvcBuilder和DefaultMockMvcBuilder，分别对应两种测试方式，即独立安装和集成Web环境测试（此种方式并不会集成真正的web环境，而是通过相应的Mock API进行模拟测试，无须启动服务器）。对于我们来说直接使用静态工厂MockMvcBuilders创建即可.

![](/Users/sulwan/study/java/learn15/1.png)



- **1. 集成 Web环境方式**

MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest  {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    } 
    
}
```

- **2. 独立测试方式**

MockMvcBuilders.standaloneSetup(Object... controllers)：通过参数指定一组控制器，这样就不需要从上下文获取了；

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest  {

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    } 
}    
复制代码
```

##### 二 . MockMvc

- 1.先看一看例子

```java
@RestController
public class UserController {

   /**
    * 创建线程安全的 map
    */
   static Map<Long, User> userMap = Collections.synchronizedMap(new HashMap<>());

   @GetMapping("/users")
   public List<User> getUserList() {
      List<User> list = new ArrayList<>(userMap.values());
      return list;
   }

   @PostMapping("/add")
   public String addUser(@RequestBody User user) {
      userMap.put(user.getId(), user);
      Map<String, String> map = new HashMap<>();
      map.put("status", "200");
      map.put("data", JSON.toJSONString(user));
      return JSON.toJSONString(map);
   }

   @GetMapping("/{id}")
   public User getUserById(@PathVariable("id") Long id) {
      return userMap.get(id);
   }

   @PutMapping("/update")
   public String updateUser(@RequestBody User user) {
      User u = userMap.get(user.getId());
      u.setName(user.getName());
      u.setAge(user.getAge());
      return "success";
   }

   @DeleteMapping("/{id}")
   public String deleteUserById(@PathVariable("id")Long id){
      userMap.remove(id);
      return "success";
   }
}
```

```java
  @RunWith(SpringRunner.class)
  @SpringBootTest
  public class UserControllerTest  {

      @Autowired
      private WebApplicationContext webApplicationContext;

      private MockMvc mockMvc;

      @Before
      public void setUp() throws Exception {
          mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      } 
      
    /** 
    * 
    * Method: getUserList() 
    * 
    */ 
    @Test
    public void testGetUserList() throws Exception {
        mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string(equalTo("[]")));
    }
      
    /** 
    * 
    * Method: addUser(@RequestBody User user) 
    * 
    */ 
    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("jamie");
        user.setAge(20);
                                        mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(user)))
            .andExpect(status().isOk())
			.andDo(print())
			.andReturn().getResponse().getContentAsString();

    } 
} 
```



方法解析：

- **perform**：**执行一个RequestBuilder请求**，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
- **get**:声明发送一个get请求的方法。MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables)：根据uri模板和uri变量值得到一个GET请求方式的。另外提供了其他的请求的方法，如：post、put、delete等。
- **andExpect**：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确（对返回的数据进行的判断）；
- **andDo**：添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；
- **andReturn**：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理（对返回的数据进行的判断）；

**整个测试过程非常有规律：**

- 1. 准备测试环境
  2. 通过MockMvc执行请求
  3. 添加验证断言
  4. 添加结果处理器
  5. 得到MvcResult进行自定义断言/进行下一步的异步请求
  6. 卸载测试环境