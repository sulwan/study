学习内容

本次主要讲解内容是如何利用Idea创建一个Spring-boot项目，依次开启java编程美好时代

首先安装Idea这里不再讲述，网上大把的，IDEA做的很到位了，直接下一步，下一步即可完成安装





1、打开Idea

2、点击 + Create New Project

3、在左侧菜单选择 Spring Initializr

4、左侧选择JDK版本，为了演示方便和与本教程其他课程配套代码运行，请选择1.8版本，如没有，请自行google进行脑补。

5、Next

6、填写如下内容

 	Group: com.haojishu
     Artifact: demo
	Type: maven
	Language:java
	Packaging: war
    Java Version: 8
    Version: 0.0.1-SNAPSHOT
    Description: Demo pro for Spring Boot
    Package: com.haojishu    

参数说明:

`GroupId`一般分为多个段，第一段为域，第二段为公司名称。域又分为org、com、cn等等许多，其中org为非营利组织，com为商业组织。举个apache公司的tomcat项目例子：这个项目的GroupId是org.apache，它的域是org（因为tomcat是非营利项目），公司名称是apache，ArtifactId是tomcat。再比如我自己建项目，可以写com.haojishu
`Type` 是指定构建程序的方式，这里我们使用Maven
`Language` 是指定开发程序的语言
 `Packaging`给出了项目的打包类型,即作为项目的发布形式 这里我们使用war，也就是在tomcat容器配合使用，当然也可以使用jar,那样就是自带集成了tomcat，直接 jar就可以运行程序了！
`Java Version` 指定开发程序，以及运行程序的java版本号
`Version` 指定你的程序的版本号
`Description` 描述你程序，可以说是简单介绍
`Package` 你的程序包名

7、

>  1、选择左侧Developer Tools  右侧选择 Spring Boot DevTools   Lombok
>  2、选择左侧Web   右侧选择 Spring Web

8、Next



下边我们添加一个例子来测试下是否创建好了Spring boot

 首先在com.haojishu上单击右键，选择新建java, 类名：siteController内容如下：

```java
package com.haojishu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

    @RequestMapping(value = "/")
    public String Hello(){
        return "Helllo World";
    }


}

```







