1. ioc生命周期

2. bean的作用域

3. beanFactory和factoryBean

4. beanFactory和ApplicationContext

5. aop

6. 事务

   

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

7. Spring MVC

   1. filter和interceptor的区别
      1. filter是servlet规范定义的，interceptor是spring定义的
      2. filter是通过filterChain实现的，interceptor是通过aop实现的
      3. filter只在方法前后执行，interceptor还可以在异常前后执行
   
8. 复习

   1. bean的生命周期
   2. 循环依赖
   3. 父子容器
   4. boot启动流程
   5. 事务实现原理
   6. 动态代理