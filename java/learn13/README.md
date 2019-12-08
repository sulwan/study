SpringBoot集成Lombok

其实之前的教程或多或少的也涉及到了lombok的知识，在编写代码的日常工作的时候，整天的get.set的时候我就郁闷，再就是看同事代码，密密麻麻的一片属性设置叫我看起来飞创不爽，后来我在一次偶然的帮别人调试代码的时候知道lombok，瞬间爱上了，所有项目几乎都是用。

`pom.xml`

```xml
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```

`Geter Setter`

```java
// 正常属性设置读取
public class User {
	
	private Integer Id;
	
	private String username;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
```

```java
// 使用Lombok
	@Getter
	@Setter
	private String email;
```

`ToString`

```java
// 正常代码	
@Override
	public String toString() {
		return "User{" +
				"Id=" + Id +
				'}';
	}
```

```java
// 使用Lombok(直接在类上添加，不用再手动了)
@ToString
public class User {
```

`@NoArgsConstructor` 创建无构造参数构造器

`@RequiredArgsConstructor`

```java
//一个class可以有很多属性，但你可能只关心其中的几个字段，那么可以使用@RequiredArgsConstructor。@NonNull将标注这个字段不应为null，初始化的时候会检查是否为空，否则抛出NullPointException。在上面的无参构造函数中被忽略了。那么，对于关注的字段标注@NonNull, @RequiredArgsConstructor则会生成带有这些字段的构造器。
@RequiredArgsConstructor
public class RequiredArgsExample {
    @NonNull private String field;
    private Date date;
    private Integer integer;
    private int i;
    private boolean b;
    private Boolean aBoolean;
}

public class RequiredArgsExample {
    @NonNull
    private String field;
    private Date date;
    private Integer integer;
    private int i;
    private boolean b;
    private Boolean aBoolean;

    public RequiredArgsExample(@NonNull String field) {
        if (field == null) {
            throw new NullPointerException("field");
        } else {
            this.field = field;
        }
    }
}
```

`@AllArgsConstructor` 初始化所有字段

```java
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConstructorExample<T> {
    private int x, y;
    @NonNull
    private T description;
}

public class ConstructorExample<T> {
    private int x;
    private int y;
    @NonNull
    private T description;
    protected ConstructorExample(int x, int y, @NonNull T description) {
        if (description == null) {
            throw new NullPointerException("description");
        } else {
            this.x = x;
            this.y = y;
            this.description = description;
        }
    }
```

`@Data` 这个才是我工作中常用的东西

@Data`是一个集合体。包含`Getter`,`Setter`,`RequiredArgsConstructor`,`ToString`,`EqualsAndHashCode

```java

```

