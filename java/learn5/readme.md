

SpringBoot整合Jpa 数据库操作

`Pom.xml`

```xml
        <!-- Spring Data JPA 依赖 :: 数据持久层框架 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```

为了演示方便，这里我们使用H2作为演示时使用

```xml
        <!-- h2 数据源连接驱动 -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
```

创建实体类

`Article`

```java
package com.haojishu.domain;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "arcticle")
public class Article implements Serializable {


	// 主建ID
	@Id
	@GeneratedValue
	private Long id;

	// 文章标题
	private String title;

	// 文章作者
	private String author;
}

```

集成数据库操作驱动仓库

`ArticleRepository`

```
package com.haojishu.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
```

`使用`

```java
package com.haojishu.controller;

import com.haojishu.domain.Article;
import com.haojishu.domain.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class siteController {

  @Autowired ArticleRepository articleRepository;

  // 显示文章列表
  @GetMapping
  public String getList(
      @RequestParam(value = "start", defaultValue = "0") Integer start,
      @RequestParam(value = "limit", defaultValue = "2") Integer limit,
      ModelMap map) {
    start = start < 0 ? 0 : start;
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(start, limit, sort);
    Page<Article> page = articleRepository.findAll(pageable);
    System.out.println(page.getNumber());
    System.out.println(page.getNumberOfElements());
    System.out.println(page.getSize());
    System.out.println(page.getTotalElements());
    System.out.println(page.getTotalPages());
    System.out.println(page.isFirst());
    System.out.println(page.isLast());
    System.out.println(page.getContent());
    return "List";
  }

  // 创建文章
  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String postArticle() {
    Article article1 = new Article();
    article1.setAuthor("作者");
    article1.setId(1L);
    article1.setTitle("标题");
    articleRepository.saveAndFlush(article1);
    return "create";
  }

  // 编辑文章
  @RequestMapping(value = "/update", method = RequestMethod.GET)
  public Article updateArticle() {
    Article article1 = new Article();
    article1.setId(1L);
    Example<Article> example = Example.of(article1);
    Optional<Article> article2 = articleRepository.findOne(example);
    return article2.orElse(null);
  }

  // 删除文章
  @RequestMapping(value = "/update", method = RequestMethod.GET)
  public String delArticle() {
    Article article1 = new Article();
    article1.setId(1L);
    articleRepository.delete(article1);
    return "delArticle";
  }
}

```

其他：

> **@Entity** 表明该类 (UserEntity) 为一个实体类，它默认对应数据库中的表名是user_entity。这里也可以写成
>
> 　　　　　　@Entity(name = "xwj_user")
>
> 　　　　　　或者
>
> 　　　　　　@Entity
> 　　　　　　@Table(name = "xwj_user", schema = "test")
>
> 　　　　　　查看@Entity注解，发现其只有一个属性name，表示其所对应的数据库中的表名
>
>  
>
> 　　**@Table** 当实体类与其映射的数据库表名不同名时需要使用 @Table注解说明，该标注与 @Entity 注解并列使用，置于实体类声明语句之前，可写于单独语　　　　　　　　　　句行，也可与声明语句同行。
> 　　　　　　@Table注解的常用选项是 name，用于指明数据库的表名
> 　　　　　　@Table注解还有两个选项 catalog 和 schema 用于设置表所属的数据库目录或模式，通常为数据库名
>
>  
>
> 如果缺省@Table注解，则class字段名即表中的字段名，所以需要@Column注解来改变class中字段名与db中表的字段名的映射规则
>
> ```
> @Column注释定义了将成员属性映射到关系表中的哪一列和该列的结构信息，属性如下：
> 　　1）name：映射的列名。如：映射tbl_user表的name列，可以在name属性的上面或getName方法上面加入；
> 　　2）unique：是否唯一；
> 　　3）nullable：是否允许为空；
> 　　4）length：对于字符型列，length属性指定列的最大字符长度；
> 　　5）insertable：是否允许插入；
> 　　6）updatetable：是否允许更新；
> 　　7）columnDefinition：定义建表时创建此列的DDL；
> 　　8）secondaryTable：从表名。如果此列不建在主表上（默认是主表），该属性定义该列所在从表的名字
> ```
>
> 　　
>
> 如果是主键id，还会用到@Id注解
>
> ```
> @Id注释指定表的主键，它可以有多种生成方式：
> 　　1）TABLE：容器指定用底层的数据表确保唯一；
> 　　2）SEQUENCE：使用数据库德SEQUENCE列莱保证唯一（Oracle数据库通过序列来生成唯一ID）；
> 　　3）IDENTITY：使用数据库的IDENTITY列莱保证唯一；
> 　　4）AUTO：由容器挑选一个合适的方式来保证唯一；
> 　　5）NONE：容器不负责主键的生成，由程序来完成。
> ```
>
>  
>
> 其中与@Id一起使用的还有另外两个注解：@GeneratedValue、@GenericGenerator，具体使用方法可参考[hibernate中的@GeneratedValue与@GenericGenerator](https://blog.csdn.net/u011781521/article/details/72210980)

