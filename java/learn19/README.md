idea 创建多模块项目（因为我们需要讲解Dubbo,所以需要先学这个）

创建项目：
	1、Create New Project
	2、选择jdk版本1.8
	3、next
	4、		Project name : demo
             Project location: d:\demo
	5、Finish
	6、在左侧菜单选择Delete
	7、右键项目名--->new--->moudle,过程和创建第一个项目一模一样，只有在输入groupId的时候已经默认是父级的，你无法自己填写，你只能填写Artifactld,这里我们填写ServiceProvider
	8、创建其他模块执行7步骤即可

编译安装dubbo-admin
1、下载源码

下载2.6.0的源码 https://github.com/apache/incubator-dubbo/releases/tag/dubbo-2.6.0

2、Idea修改dubbo-admin配置文件
	dubbo-dubbo-2.6.0\dubbo-admin\src\main\webapp\WEB-INF\dubbo.properties
3、加载到tomcat中运行，账户密码是admin,admin

知道了这两点，下次就可以讲解dubbo了，
为啥更新这么慢，因为快过年了了，我本职工作是卖肉的，毕竟我需要养家糊口，吃饭啊！