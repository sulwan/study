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
