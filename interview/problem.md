1. jdk

   1. nio
      1. 三大组件？
      2. epoll原理？和select的区别？边缘触发和水平触发？惊群问题？
   2. 集合
      1. set和list区别？
      2. ArrayList扩容机制？
      3. ArratList和Vertor区别？
      4. ArrayList和LinkedList区别？
      5. Hashmap、hashTable和hashSet区别？
      6. HashMap
         1. put流程？
         2. 扩容流程？
         3. hashMap的长度为什么是2的幂次方？
         4. 为什么要把hashCOde右移16位？
         5. 并发安全？
      7. ConcurretnHashMap
      8. jdk新特性
         1. 1.8的新特性
            1. lamdba
            2. 接口的deafult方法
            3. stream
            4. localDatetime
            5. Optional
            6. 独占缓存行注解
         2. jdk9特性
         3. jdk15特性：
            1. 去除synchronized的偏向锁

2. jvm

   1. jvm的内存结构？
   2. 对象创建过程？
   3. 什么情况发生OOm异常
   4. 判断对象是否是垃圾？
   5. GC ROOT
   6. 4中引用？
   7. 方法区的回收？
   8. 垃圾回收算法？
   9. 内存分配和回收策略？
   10. 垃圾回收器？
   11. G1
   12. CMS
   13. zgc
   14. 分代收集？弱分代假设？分代收集垃圾回收器怎么工作？
   15. 类加载过程？类加载方式？tomcat如何破坏双亲委派模型？
   16. 什么情况发送ygc？什么情况下发送full gc？
   17. jvm怎么调优？一般从哪里入手？
   18. 为什么有垃圾回收还会发生内存泄漏？
   19. 内存持续上升？怎么定位问题？
   20. jvm参数？

3. juc

   1. 多线程理解？什么是并发安全？

   2. java实现同步方式？

   3. 如何创建一个线程？如何指定线程的执行逻辑？

   4. 线程的6种状态？

   5. 对象头？每个对象占多大内存？markWord？monitor？

   6. volatile？

      1. 可见性和有序性怎么保证？
      2. 缓存一致性协议怎么保证可见性？

   7. synchronized

      1. 锁升级？
      2. jdk15为什么去掉偏向锁？

   8. volatile和synchronized的区别？

   9. lock和synchronized的区别？

   10. Java的内存模型？

   11. as-if-serial?

   12. final？

   13. aqs

   14. threadLocal

   15. fastThreadLocal

   16. 线程池

       1. 七个核心参数？

       2. 

       3. 自定义拒绝策略？

          > 序列化 然后保存起来

4. 集合

   1. hashMap
   2. concurrentHashMap
   3. copyOnWriteList

5. 计网

   1. http
   2. tcp
   3. udp
   4. http和https的区别？
   5. 对称加密和非对称加密？
   6. http请求和响应报文格式？
   7. http常见状态码和请求头?
   8. 长连接和短连接？
   9. redis session共享？
   10. http only？
   11. http1.1新特性？
   12. http2.0新 特性？dubbo使用http2
   13. restful？幂等？
   14. post和get区别？
   15. https连接过程
   16. 

6. spring

   1. bean的生命周期？
   2. bean的scope？
   3. 静态代理和动态代理的区别？
   4. beanFactory和applicationContext那个才是ioc容器？
   5. 三级缓存怎么实现？
   6. spring aop实现原理？
   7. spring mvc流程？
   8. spring boot自动装配原理？
   9. spring 事务实现方式？
   10. 事务传播级别？？
   11. 源码？
       1. spring session源码
          1. MapSession.getId(): 产生一个UUID作为session

7. kafka

   1. kafka为什么这么快？
   2. kafka的日志段如何读写？
   3. kafka的消息是推还是拉，怎么实现的？
   4. 怎么保证消息不丢失？
   5. 如何处理重复消息？
   6. 消息的有序性？
   7. 消息的堆积处理？
   8. kafka的主题与分区内部是如何存储的，有什么特点？
   9. 与传统的消息系统相比，kafka的消费模型有什么优点？
   10. kafka如何实现分布式的数据存储与数据读取？
   11. 源码？

8. mysql

   1. 一条select语句的执行过程？
   2. 一条update sql执行过程？
   3. innodb和myisam的区别？
   4. innodb的特性？
   5. 为什么自增主键不连续？
   6. 自增主键理解？
   7. innodb为什么推荐自增id？
   8. 索引
      1. 为什么用b+树
      2. 索引类型？
      3. 索引覆盖？回表？索引下推？联合索引？
      4. 索引失效场景？
      5. 最左匹配原则？
      6. 联合索引建立规则？
      7. 前缀索引？
      8. 百万级别数据怎么删除？
      9. 普通索引和联合索引怎么选择？
   9. 锁
   10. 事务
       1. acid？
       2. mvcc？
   11. explain？
   12. 脏页？怎样刷新脏页？
   13. 一条sql很慢怎么定位？
   14. sql优化？
   15. mysql的三种日志？
   16. 什么是撞库、脱库、洗库？
   17. 主从复制？
   18. 如何分库分表？

9. redis

   1. 为什么选择redis？
   2. 5种基本数据结构+redis module+redis stream
   3. redis为什么快？
   4. redis渐近式hash?
   5. redis的线程模型？
   6. redis6.0为何使用多线程？
   7. 缓存过期淘汰策略？
   8. 内存淘汰机制？
   9. 主从、哨兵和集群？
   10. redis的aof和rdb？
   11. redis的事务？
   12. 缓存击穿、雪崩、穿透怎么解决？
   13. watch和cas乐观锁？
   14. redis如何实现分布式锁？
   15. 并发竞争？
   16. 异步队列？
   17. 延时队列？
   18. redis如何保证双写一致性？先更新缓存还是先更新数据库
   19. redis内存耗尽后怎么办？
   20. 1亿个key，10万个key是已知前缀，怎么把它们全找出来？
   21. 如何排查redis性能问题？
   22. redis的hot key？
   23. 主库挂怎么办》？

10. dubbo

    1. dubbo支持的协议？
    2. dubbo怎么实现熔断？
    3. dubbo调用过程？
    4. 怎么实现限流？
    5. 序列化实现原理？
    6. dubbo怎么支持事务？

11. 系统设计

    1. 如何设计一个高并发系统？
    2. 

12. netty

13. sentinel

14. 分布式

    1. 分布式和微服务的理解？
    2. 为什么要分布式？集群？负载均衡？
    3. cap理论？
    4. paxos和raft？
    5. 分布式事务？
    6. 分布式ID?
    7. rpc？
    8. 一致性hash？
    9. 高可用？限流？降级？熔断？集群？

15. linux

    1. tcpdump抓包过程？
       1. 先查看网卡：ip a | grep
       2. tcpdump -i ens192 -nn host 192.168.3.63 port 5533 -Xs0

16. 其他

    1. 怎么跨线程传递THreadLocal

       > 使用阿里的TTL：对ThreadLocal进行store和relay

    2. 需求多变时，怎么设计？

    3. 使用hashMap实现一个带时间的缓存？