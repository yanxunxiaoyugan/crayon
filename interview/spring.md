1. 如何定义一个bean

   1. xml文件
   2. @Bean + Configuration + Compoentscan
   3. @Component + Compoentscan
   4. 自定义BeanDefinition，获取BeanFactory，然后把beanDefitioin注册进去

2. BeanDefinition

   1. 属性
      1. scope（context中只有singleton和prototype）
      2. bean来源（自定义、配置文件、spring内部bean）
      3. ini-method
      4. 是否懒加载

3. 静态代理和动态代理、jdk代理和Cglib代理

   1. 静态代理
   2. 动态代理
   3. 

4. ioc生命周期

5. bean的作用域

6. beanFactory和factoryBean

7. beanFactory和ApplicationContext

8. aop

9. 事务

   

   > 1. 顶层接口
   >
   > - 
   >   - **PlatformTransactionManager：** （平台）事务管理器
   >   - **TransactionDefinition：** 事务定义信息(事务隔离级别、传播行为、超时、只读、回滚规则)
   >   - **TransactionStatus：** 事务运行状态
   >
   > 2. 事务传播行为
   >
   >    **TransactionDefinition.PROPAGATION_REQUIRED：** 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
   >
   >    **TransactionDefinition.PROPAGATION_SUPPORTS：** 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
   >
   >    **TransactionDefinition.PROPAGATION_MANDATORY：** 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。（mandatory：强制性）
   >
   > 
   >
   >    **TransactionDefinition.PROPAGATION_REQUIRES_NEW：** 创建一个新的事务，如果当前存在事务，则把当前事务挂起。
   >
   >    **TransactionDefinition.PROPAGATION_NOT_SUPPORTED：** 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
   >
   >    **TransactionDefinition.PROPAGATION_NEVER：** 以非事务方式运行，如果当前存在事务，则抛出异常。
   >
   > 
   >
   >    **TransactionDefinition.PROPAGATION_NESTED：** 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。
   >
   > 3. 回滚规则
   >
   >    1. 默认情况下，事务只有遇到运行期异常时才会回滚，而在遇到检查型异常时不会回滚
   >
   > 4. 事务状态啊
   >
   >    1. public interface TransactionStatus{
   >           boolean isNewTransaction(); // 是否是新的事物
   >           boolean hasSavepoint(); // 是否有恢复点
   >           void setRollbackOnly();  // 设置为只回滚
   >           boolean isRollbackOnly(); // 是否为只回滚
   >           boolean isCompleted; // 是否已完成
   >       } 
   >

   1. 事务失效场景
      1. 调用本类方法
      2. 方法不是public：如果要加@Transactional在非public方法上，可以开启Aspectj代理
      3. 数据库不支持事务
      4. 没有被spring管理
      5. 异常被吃掉

10. Spring MVC

   1. filter和interceptor的区别
      1. filter是servlet规范定义的，interceptor是spring定义的
      2. filter是通过filterChain实现的，interceptor是通过aop实现的
      3. filter只在方法前后执行，interceptor还可以在异常前后执行

11. 复习

   12. bean的生命周期

   13. 循环依赖

   14. 父子容器

   16. springboot

          1. 核心类
             
                1. AnnotationConfigServletWebServerApplicationContext
                2. SpringFactoriesLoader: 负责加载META-INF/spring.factories，并放在Map<ClassLoader, MultiValueMap<String（接口全类名）, String（子类全类名）>>中
                
          2. 启动流程

                > 1. springApplication构造方法
                >    1. SpringApplication->SpringFactoriesLoader.(加载所有的spring.factories文件)
                >    2. 获取spring.factories文件的ApplicationContextInitializer的子类，并实例化这些子类并根据order排序
                >    3. springApplication把spring.factories中的ApplicationContextInitializer的子类实例赋值给initializers
                >    4. springApplication把spring.factories中的ApplicationListener的子类实例赋值给listeners
                >    5. 根据new RuntimeException().getStackTrace()获取当前线程的栈帧，，找到栈帧中第一个方法为Main的类，设置成mainApplicationClass
                > 2. SpringApplication的Run方法
                >    1. 获取spring.factories的SpringApplicationRunListener类，调用他们的start方法
                >    2. 准备Environment
                >    3. 输出Banner图片
                >    4. 创建ApplicationContext：springMVC创建的是AnnotationConfigServletWebServerApplicationContext，然后在构造方法创建AnnotatedBeanDefinitionReader 和ClassPathBeanDefinitionScanner，在GenericApplicationContext创建DedaultListableBeanFactory。webflux创建的是AnnotationConfigReactiveWebServerApplicationContext。
                >    5. 获取spring.factories的SpringBootExceptionReporter子类
                >    6. repareContext（context，environment，listeners，args，banner）
                >    7. refreshContext
                >       1. prepareRefresh
                >       2. obtianFreshBeanFactory
                >       3. prepareBeanFactory
                >       4. postProcessBeanFactory(beanFactory)
                >       5. invokeBeanFactoryPostProcessors
                >       6. registerBeanPostProcessor
                >       7. intiMessageSource
                >       8. initApplicationEventMulticaster
                >       9. onRefresh
                >       10. registListeners
                >       11. finishBeanFactoryInitialization
                >       12. finishRefresh
                >    8. atferRefresh
                >    9. listeners.started(context)
                >    10. callRunners(context,applicationArguments)
                >    11. listeners.running(context)
                >
                
          3. 自动装配原理

                   1. 

   16. boot启动流程

   18. spring aop

          1. 概念

             > aop要实现的就是在我们原来写的代码上，进行一定的扩展。比如说在方法执行前后、方法异常抛出后等地方进行拦截，然后做增强处理。aop的实现是把方法的生命周期告诉我们，然后我们去实现一个代理，通过代理对方法的生命周期进行增强。

          2. 基础名词

                1. pointcut
                2. advice
                3. advisor/aspect
                4. proxy

   19. 事务实现原理

   20. 代理

          1. 静态代理
          2. 动态代理