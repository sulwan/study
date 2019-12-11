SpringBoot整合Zookeeper

在讲解这个的时候我们需要引用一个第三方插件curator， http://curator.apache.org/ 他也是基金会的，具体为啥，我觉得直接记住用这个就可以了，因为自己手写客户端太痛苦，还不如人家的好，干脆用人家的吧！

## 项目组件

| 名称      | 描述                                                         |
| --------- | ------------------------------------------------------------ |
| Recipes   | Zookeeper典型应用场景的实现，这些实现是基于Curator Framework。 |
| Framework | Zookeeper API的高层封装，大大简化Zookeeper客户端编程，添加了例如Zookeeper连接管理、重试机制等。 |
| Utilities | 为Zookeeper提供的各种实用程序。                              |
| Client    | Zookeeper client的封装，用于取代原生的Zookeeper客户端（ZooKeeper类），提供一些非常有用的客户端特性。 |
| Errors    | Curator如何处理错误，连接问题，可恢复的例外等。              |

 目前Curator有2.x.x和3.x.x两个系列的版本，支持不同版本的Zookeeper。其中Curator 2.x.x兼容Zookeeper的3.4.x和3.5.x。而Curator 3.x.x只兼容Zookeeper 3.5.x，并且提供了一些诸如动态重新配置、watch删除等新特性。 
Curator 4.0对ZooKeeper 3.5.x有硬依赖关系
如果部署的ZooKeeper服务为3.5.x，则直接使用Curator 4.0即可
Curator 4.0同时提供了对ZooKeeper 3.4.x的软兼容配置，需要做两件事：
1.Curator 4.0排除ZooKeeper
2.pom.xml单独引入ZooKeeper

综合所述，咱们还是使用2.X比较好！

`POM.XML`

```xml
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>2.13.0</version>
</dependency>
```

## 数据节点操作

### 创建数据节点

**Zookeeper的节点创建模式：**

- PERSISTENT：持久化
- PERSISTENT_SEQUENTIAL：持久化并且带序列号
- EPHEMERAL：临时
- EPHEMERAL_SEQUENTIAL：临时并且带序列号

**创建一个节点，初始内容为空 **



```css
client.create().forPath("path");
```

注意：如果没有设置节点属性，节点创建模式默认为持久化节点，内容默认为空

**创建一个节点，附带初始化内容**



```css
client.create().forPath("path","init".getBytes());
```

**创建一个节点，指定创建模式（临时节点），内容为空**



```css
client.create().withMode(CreateMode.EPHEMERAL).forPath("path");
```

**创建一个节点，指定创建模式（临时节点），附带初始化内容**



```css
client.create().withMode(CreateMode.EPHEMERAL).forPath("path","init".getBytes());
```

**创建一个节点，指定创建模式（临时节点），附带初始化内容，并且自动递归创建父节点**



```java
client.create()
      .creatingParentContainersIfNeeded()
      .withMode(CreateMode.EPHEMERAL)
      .forPath("path","init".getBytes());
```

这个creatingParentContainersIfNeeded()接口非常有用，因为一般情况开发人员在创建一个子节点必须判断它的父节点是否存在，如果不存在直接创建会抛出NoNodeException，使用creatingParentContainersIfNeeded()之后Curator能够自动递归创建所有所需的父节点。

### 删除数据节点

**删除一个节点**



```cpp
client.delete().forPath("path");
```

注意，此方法只能删除**叶子节点**，否则会抛出异常。

**删除一个节点，并且递归删除其所有的子节点**



```css
client.delete().deletingChildrenIfNeeded().forPath("path");
```

**删除一个节点，强制指定版本进行删除**



```css
client.delete().withVersion(10086).forPath("path");
```

**删除一个节点，强制保证删除**



```css
client.delete().guaranteed().forPath("path");
```

guaranteed()接口是一个保障措施，只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到删除节点成功。

**注意：**上面的多个流式接口是可以自由组合的，例如：



```css
client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(10086).forPath("path");
```

### 读取数据节点数据

**读取一个节点的数据内容**



```css
client.getData().forPath("path");
```

注意，此方法返的返回值是byte[ ];

**读取一个节点的数据内容，同时获取到该节点的stat**



```bash
Stat stat = new Stat();
client.getData().storingStatIn(stat).forPath("path");
```

### 更新数据节点数据

**更新一个节点的数据内容**



```css
client.setData().forPath("path","data".getBytes());
```

注意：该接口会返回一个Stat实例

**更新一个节点的数据内容，强制指定版本进行更新**



```css
client.setData().withVersion(10086).forPath("path","data".getBytes());
```

### 检查节点是否存在



```css
client.checkExists().forPath("path");
```

注意：该方法返回一个Stat实例，用于检查ZNode是否存在的操作. 可以调用额外的方法(监控或者后台处理)并在最后调用forPath( )指定要操作的ZNode

### 获取某个节点的所有子节点路径



```css
client.getChildren().forPath("path");
```

注意：该方法的返回值为List<String>,获得ZNode的子节点Path列表。 可以调用额外的方法(监控、后台处理或者获取状态watch, background or get stat) 并在最后调用forPath()指定要操作的父ZNode

### 事务

CuratorFramework的实例包含inTransaction( )接口方法，调用此方法开启一个ZooKeeper事务. 可以复合create, setData, check, and/or delete 等操作然后调用commit()作为一个原子操作提交。一个例子如下：



```java
client.inTransaction().check().forPath("path")
      .and()
      .create().withMode(CreateMode.EPHEMERAL).forPath("path","data".getBytes())
      .and()
      .setData().withVersion(10086).forPath("path","data2".getBytes())
      .and()
      .commit();
```