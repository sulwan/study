本章讲解Aop，其实本身我不应该先更新这张，这章本身应该在上一张综合案例之后讲解，可是因为一个团队写的代码实在叫我受不了，上班工作，到下班一直是垃圾代码，垃圾架构，说过他两三次，我已经放弃，决定写这篇有感而发写了这个文章，其实很多时候我们从事工作，不是一开始的就参与，之前代码肯定有这样那样的问题，不要抱怨，干就完了，可是很多年轻人不懂，再就是自己写的代码真的比过去作者好很多吗？如何在不破坏之前的结构设计心得逻辑，这个时候其实切面用到！看到的作者体会下我这句话！

@Aspect:作用是把当前类标识为一个切面供容器读取

JointPoint(连接点): 程序执行过程中明确的点，一般是方法的调用

Advice(通知): AOP在特定的切入点上执行的增强处理:

```java
@Pointcut("execution(* com.spring.two.UserMangeImpl.*(..))")
```

**切入点使用示例**

`execution：`使用“execution(方法表达式)”匹配方法执行；

| **模式**                                                     | **描述**                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| public * *(..)                                               | 任何公共方法的执行                                           |
| * cn.javass..IPointcutService.*()                            | cn.javass包及所有子包下IPointcutService接口中的任何无参方法  |
| * cn.javass..*.*(..)                                         | cn.javass包及所有子包下任何类的任何方法                      |
| * cn.javass..IPointcutService.*(*)                           | cn.javass包及所有子包下IPointcutService接口的任何只有一个参数方法 |
| * (!cn.javass..IPointcutService+).*(..)                      | 非“cn.javass包及所有子包下IPointcutService接口及子类型”的任何方法 |
| * cn.javass..IPointcutService+.*()                           | cn.javass包及所有子包下IPointcutService接口及子类型的的任何无参方法 |
| * cn.javass..IPointcut*.test*(java.util.Date)                | cn.javass包及所有子包下IPointcut前缀类型的的以test开头的只有一个参数类型为java.util.Date的方法，注意该匹配是根据方法签名的参数类型进行匹配的，而不是根据执行时传入的参数类型决定的如定义方法：public void test(Object obj);即使执行时传入java.util.Date，也不会匹配的； |
| * cn.javass..IPointcut*.test*(..) throws IllegalArgumentException, ArrayIndexOutOfBoundsException | cn.javass包及所有子包下IPointcut前缀类型的的任何方法，且抛出IllegalArgumentException和ArrayIndexOutOfBoundsException异常 |
| * (cn.javass..IPointcutService+&& java.io.Serializable+).*(..) | 任何实现了cn.javass包及所有子包下IPointcutService接口和java.io.Serializable接口的类型的任何方法 |
| @java.lang.Deprecated * *(..)                                | 任何持有@java.lang.Deprecated注解的方法                      |
| @java.lang.Deprecated @cn.javass..Secure  * *(..)            | 任何持有@java.lang.Deprecated和@cn.javass..Secure注解的方法  |
| @(java.lang.Deprecated \|\| cn.javass..Secure) * *(..)       | 任何持有@java.lang.Deprecated或@ cn.javass..Secure注解的方法 |
| (@cn.javass..Secure  *) *(..)                                | 任何返回值类型持有@cn.javass..Secure的方法                   |
| *  (@cn.javass..Secure *).*(..)                              | 任何定义方法的类型持有@cn.javass..Secure的方法               |
| * *(@cn.javass..Secure (*) , @cn.javass..Secure (*))         | 任何签名带有两个参数的方法，且这个两个参数都被@ Secure标记了，如public void test(@Secure String str1, @Secure String str1); |
| * *((@ cn.javass..Secure *))或* *(@ cn.javass..Secure *)     | 任何带有一个参数的方法，且该参数类型持有@ cn.javass..Secure；如public void test(Model model);且Model类上持有@Secure注解 |
| * *(@cn.javass..Secure (@cn.javass..Secure *) ,@ cn.javass..Secure (@cn.javass..Secure *)) | 任何带有两个参数的方法，且这两个参数都被@ cn.javass..Secure标记了；且这两个参数的类型上都持有@ cn.javass..Secure； |
| * *(java.util.Map<cn.javass..Model, cn.javass..Model>, ..)   | 任何带有一个java.util.Map参数的方法，且该参数类型是以< cn.javass..Model, cn.javass..Model >为泛型参数；注意只匹配第一个参数为java.util.Map,不包括子类型；如public void test(HashMap<Model, Model> map, String str);将不匹配，必须使用“* *(java.util.HashMap<cn.javass..Model,cn.javass..Model>, ..)”进行匹配；而public void test(Map map, int i);也将不匹配，因为泛型参数不匹配 |
| * *(java.util.Collection<@cn.javass..Secure *>)              | 任何带有一个参数（类型为java.util.Collection）的方法，且该参数类型是有一个泛型参数，该泛型参数类型上持有@cn.javass..Secure注解；如public void test(Collection<Model> collection);Model类型上持有@cn.javass..Secure |
| * *(java.util.Set<? extends HashMap>)                        | 任何带有一个参数的方法，且传入的参数类型是有一个泛型参数，该泛型参数类型继承与HashMap；**Spring AOP目前测试不能正常工作** |
| * *(java.util.List<? super HashMap>)                         | 任何带有一个参数的方法，且传入的参数类型是有一个泛型参数，该泛型参数类型是HashMap的基类型；如public voi test(Map map)；**Spring AOP目前测试不能正常工作** |
| * *(*<@cn.javass..Secure *>)                                 | 任何带有一个参数的方法，且该参数类型是有一个泛型参数，该泛型参数类型上持有@cn.javass..Secure注解；**Spring AOP目前测试不能正常工作** |

 

`within：`使用“within(类型表达式)”匹配指定类型内的方法执行；

| **模式**                             | **描述**                                                     |
| ------------------------------------ | ------------------------------------------------------------ |
| within(cn.javass..*)                 | cn.javass包及子包下的任何方法执行                            |
| within(cn.javass..IPointcutService+) | cn.javass包或所有子包下IPointcutService类型及子类型的任何方法 |
| within(@cn.javass..Secure *)         | 持有cn.javass..Secure注解的任何类型的任何方法必须是在目标对象上声明这个注解，在接口上声明的对它不起作用 |

 

`this：`使用“this(类型全限定名)”匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口方法也可以匹配；注意this中使用的表达式必须是类型全限定名，不支持通配符；

| **模式**                                                     | **描述**                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| this(cn.javass.spring.chapter6.service.IPointcutService)     | 当前AOP对象实现了 IPointcutService接口的任何方法             |
| this(cn.javass.spring.chapter6.service.IIntroductionService) | 当前AOP对象实现了 IIntroductionService接口的任何方法也可能是引入接口 |

 

`target：`使用“target(类型全限定名)”匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；注意target中使用的表达式必须是类型全限定名，不支持通配符；

| **模式**                                                     | **描述**                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| target(cn.javass.spring.chapter6.service.IPointcutService)   | 当前目标对象（非AOP对象）实现了 IPointcutService接口的任何方法 |
| target(cn.javass.spring.chapter6.service.IIntroductionService) | 当前目标对象（非AOP对象） 实现了IIntroductionService 接口的任何方法不可能是引入接口 |

 

`args：`使用“args(参数类型列表)”匹配当前执行的方法传入的参数为指定类型的执行方法；注意是匹配传入的参数类型，不是匹配方法签名的参数类型；参数类型列表中的参数必须是类型全限定名，通配符不支持；args属于动态切入点，这种切入点开销非常大，非特殊情况最好不要使用；

| **模式**                       | **描述**                                                     |
| ------------------------------ | ------------------------------------------------------------ |
| args (java.io.Serializable,..) | 任何一个以接受“传入参数类型为 java.io.Serializable” 开头，且其后可跟任意个任意类型的参数的方法执行，args指定的参数类型是在运行时动态匹配的 |

 

`@within：`使用“@within(注解类型)”匹配所以持有指定注解类型内的方法；注解类型也必须是全限定类型名；

| **模式**                                  | **描述**                                                     |
| ----------------------------------------- | ------------------------------------------------------------ |
| @within cn.javass.spring.chapter6.Secure) | 任何目标对象对应的类型持有Secure注解的类方法；必须是在目标对象上声明这个注解，在接口上声明的对它不起作用 |



`@target：`使用“@target(注解类型)”匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；注解类型也必须是全限定类型名； 

 

| **模式**                                   | **描述**                                                     |
| ------------------------------------------ | ------------------------------------------------------------ |
| @target (cn.javass.spring.chapter6.Secure) | 任何目标对象持有Secure注解的类方法；必须是在目标对象上声明这个注解，在接口上声明的对它不起作用 |

 

`@args：`使用“@args(注解列表)”匹配当前执行的方法传入的参数持有指定注解的执行；注解类型也必须是全限定类型名；*

| **模式**                                 | **描述**                                                     |
| ---------------------------------------- | ------------------------------------------------------------ |
| @args (cn.javass.spring.chapter6.Secure) | 任何一个只接受一个参数的方法，且方法运行时传入的参数持有注解 cn.javass.spring.chapter6.Secure；动态切入点，类似于arg指示符； |

  

`@annotation：`使用“@annotation(注解类型)”匹配当前执行方法持有指定注解的方法；注解类型也必须是全限定类型名；*

| **模式**                                       | **描述**                                                     |
| ---------------------------------------------- | ------------------------------------------------------------ |
| @annotation(cn.javass.spring.chapter6.Secure ) | 当前执行方法上持有注解 cn.javass.spring.chapter6.Secure将被匹配 |

  

`bean：`使用“bean(Bean id或名字通配符)”匹配特定名称的Bean对象的执行方法；Spring ASP扩展的，在AspectJ中无相应概念；*

| **模式**       | **描述**                                    |
| -------------- | ------------------------------------------- |
| bean(*Service) | 匹配所有以Service命名（id或name）结尾的Bean |

AspectJ 支持 5 种类型的通知注解:

> @Before: 前置通知, 在方法执行之前执行
>
> @After: 后置通知, 在方法执行之后执行 。
>
> @AfterRunning: 返回通知, 在方法返回结果之后执行
>
> @AfterThrowing: 异常通知, 在方法抛出异常之后
>
> @Around: 环绕通知, 围绕着方法执行



## @Around环绕通知

```java
@Around("execution(public * com.haojishu.controller.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pdj){
        /*result为连接点的放回结果*/
        Object result = null;
        String methodName = pdj.getSignature().getName();

        /*前置通知方法*/
        System.out.println("前置通知方法>目标方法名：" + methodName + ",参数为：" + Arrays.asList(pdj.getArgs()));

        /*执行目标方法*/
        try {
            result = pdj.proceed();

            /*返回通知方法*/
            System.out.println("返回通知方法>目标方法名" + methodName + ",返回结果为：" + result);
        } catch (Throwable e) {
            /*异常通知方法*/
            System.out.println("异常通知方法>目标方法名" + methodName + ",异常为：" + e);
        }

        /*后置通知*/
        System.out.println("后置通知方法>目标方法名" + methodName);

        return result;
    }
}
```

