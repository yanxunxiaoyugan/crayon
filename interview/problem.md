1. jdk

   1. nio
      1. 三大组件？
      2. epoll原理？和select的区别？边缘触发和水平触发？惊群问题？

2. jvm

   1. jvm的内存结构？
   2. 分代收集？弱分代假设？
   3. 类加载过程？tomcat如何破坏双亲委派模型？
   4. 什么情况发送ygc？什么情况下发送full gc？
   5. jvm怎么调优？一般从哪里入手？

3. juc

   1. volatile？

      1. 可见性和有序性怎么保证？
      2. 缓存一致性协议怎么保证可见性？

   2. synchronized

      1. 锁升级？
      2. jdk15为什么去掉偏向锁？

   3. Java的内存模型？

   4. aqs

   5. 线程池

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
   4. 索引
      1. 为什么用b+树
   5. 锁
   6. mvcc？
   7. mysql的三种日志？
   8. 什么是撞库、脱库、洗库？
   9. 主从复制？
   10. 如何分库分表？

9. redis

   1. 5种基本数据结构+redis module+redis stream
   2. redis为什么快？
   3. 淘汰策略？
   4. 淘汰机制？
   5. 主从、哨兵和集群？
   6. redis的aof和rdb？
   7. redis的事务？
   8. 缓存击穿、雪崩、穿透怎么解决？
   9. watch和cas乐观锁？
   10. redis如何实现分布式锁？
   11. 并发竞争？
   12. redis6.0为何使用多线程？
   13. redis如何保证双写一致性？先更新缓存还是先更新数据库
   14. redis内存耗尽后怎么办？
   15. 1亿个key，10万个key是已知前缀，怎么把它们全找出来？
   16. 如何排查redis性能问题？

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

14. linux

    1. tcpdump抓包过程？
       1. 先查看网卡：ip a | grep
       2. tcpdump -i ens192 -nn host 192.168.3.63 port 5533 -Xs0

15. 其他

    1. 怎么跨线程传递THreadLocal
    2. 需求多变时，怎么设计？
    3. 使用hashMap实现一个带时间的缓存？