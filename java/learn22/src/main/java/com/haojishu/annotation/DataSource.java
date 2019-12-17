package com.haojishu.annotation;

import com.haojishu.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * 　　取值(ElementType)有：
 *
 * <p>1.CONSTRUCTOR:用于描述构造器 　　　　2.FIELD:用于描述域 　　　　3.LOCAL_VARIABLE:用于描述局部变量 　　　　4.METHOD:用于描述方法
 * 　　　　5.PACKAGE:用于描述包 　　　　6.PARAMETER:用于描述参数 　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 */
@Target({ElementType.METHOD, ElementType.TYPE})

/**
 * 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 * 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 * 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 */
@Retention(RetentionPolicy.RUNTIME)
/** Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。 如果一个类型声明被注释了文档化，它的注释成为公共API的一部分 */
@Documented
public @interface DataSource {
  /** 切换数据源的名称 */
  public DataSourceType value() default DataSourceType.MASTER;
}
