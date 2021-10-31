1. jdk

   1. nio
      1. 三大组件？
      2. epoll原理？和select的区别？边缘触发和水平触发？惊群问题？
   2. 集合
      1. set和list区别？

         > set:无序 ，不可重复，时间复杂度（插入：O（1），contain：O（1））
         >
         > list：有序，可重复，list可用过下标来访问，时间复杂度（插入：O（n），contain：O（n））

      2. ArrayList扩容机制？

         > 

      3. ArratList和Vertor区别？

         > ArrayList: 线程不安全，扩容时1.5倍扩容
         >
         > Vertor: 线程安全，扩容时翻倍扩容

      4. ArrayList和LinkedList区别？

         > ArrayList:
         >
         > LinkedList:

      5. Hashmap、hashTable和hashSet区别？

      6. HashMap
         1. put流程？

            > 1. 现在entry数组有没有初始化，如果没有就初始化
            > 2. 计算参数key的hash，算出该key应该在数组中的位置
            > 3. 拿到该位置的node，

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

      > 1. 类加载器：url 
      > 2. 运行时数据区：
      > 3. 执行引擎，本地方法库

   2. 对象创建过程？

      > 1. 如果开启了逃逸分析，会分析该变量是不是线程私有的，如果是私有的会尝试标量替换把对象打散，把其中的基础数据类型在栈上分配。
      > 2. 如果不能在栈上分配，会尝试在TLAB(thread local aclcation buffer：默认大小是eden区的1%)上分配。（Tlab是为了加快对象分配速度，防止每次分配对象都需要cas，每个TLAB只能由一个线程创建对象，但是创建出来的对象是共享的）
      > 3. 如果TLAB分配失败，如果内存是规整的就进行指针碰撞，否则就在空闲列表找一块内存分配。

   3. 什么情况发生OOm异常

      > 

   4. 判断对象是否是垃圾？

      > java里使用可达性算法，从GC ROOTS出发，能被GC ROOTS找到的对象就是存活的对象，否则就是垃圾对象

   5. GC ROOT

      > 1. 虚拟机栈引用的对象
      > 2. 方法区类属性（static修饰的）引用的对象
      > 3. 方法区常量池引用的对象
      > 4. 本地方法栈中引用的对象
      > 5. 被同步锁（synchronized）持有的对象
      > 6. jvm自身只有的对象，比如系统类加载器，基本数据类型对应的class对象
      > 7. 对于YGC，老年代对新生代的引用也可以作为GC ROOT

   6. 4中引用？

      > 1. 强引用：一般就是new出来的对象
      > 2. 软引用（对应SoftRefenerce）：只要内存足够，就不会被回收。软引用可以配合ReferenceQueue一起使用（在创建软引用的时候在构造方法传入一个ReferenceQueue），当软引用已经被回收时，会把该对象加入ReferenceQueue，调用ReferenceQueue.poll()可以知道哪些软引用会被回收。用来做缓存
      > 3. 弱引用（对应WeekRefenerce）：下次垃圾回收就会回收。也可以配合ReferenceQueue一起使用
      > 4. 虚引用（对应platformRefenerce）：一定要配合ReferenceQueue一起使用。jdk8的platformRefenerce不会对gc产生影响。
      > 5. FinalReference:  如果某个对象实现了非空的Finalize()方法，那么会在编译时候生成一个Finalizer。所有的此类对象形成一个Finalizer链条。在gc的时候会取出对象执行finalize方法，并把该Finalizer从链条中删除，所以finalize方法只会执行一次（单独的线程执行）。

   7. 贫寒太透明Reference和finalizer：![image-20211030155536917](image-20211030155536917.png)--------------------------------------------------------------------------------- ![image-20211030155618428](image-20211030155618428.png)

   8. 软引用为什么可以解决OOM问题？

      > ​	假如有一个应用需要读取大量的本地图片，如果每次读取图片都从硬盘读取，则会严重影响性能，但是如果全部加载到内存当中，又有可能造成内存溢出，此时使用软引用可以解决这个问题。
      >
      > 　设计思路是：用一个HashMap来保存图片的路径 和 相应图片对象关联的软引用之间的映射关系，在内存不足时，JVM会自动回收这些缓存图片对象所占用的空间，从而有效地避免了OOM的问题。在Android开发中对于大量图片下载会经常用到。

   9. 虚引用的作用？

      > 1. 之前mysql使用finalize方法来清理资源，先已经改为虚引用了（finalize线程可能得不到执行，导致无法gc）

   10. 为什么jdk的垃圾回收不回收 虚引用？

       > ​    在Java 8的实现中，确保对象在真正GC前能被对应的ReferenceQueue处理，所以将对象标记为活跃，不回收对象
       >
       > ​    显然，在这种情况下，会导致本可以回收的对象无法回收的问题，所以在Java 9中，确保PhantomReference指向的对象在回收后（而不是原来的回收前），会被对应的ReferenceQueue处理，这样在一定程度上保证了功能，又修复了这个问题

   11. 方法区的回收？

   12. 垃圾回收算法？

       > 1. 标记清楚：
       > 2. 复制算法：
       > 3. 标记整理算法：

   13. 内存分配和回收策略？

   14. 垃圾回收器？

   15. G1

   16. CMS

       1. 初始标记：需要stw，只标记GC ROOT能直接关联的对象
       2. 并发标记：在初始标记的基础上继续搜索
       3. 预清理：
       4. 可被终止的预清理
       5. 重新标记
       6. 并发清除：
       7. 并发重置状态

   17. zgc

   18. 三色标记法？浮动垃圾？

   19. 分代收集？弱分代假设？分代收集垃圾回收器怎么工作？

       > 1. 弱分代假设的两个理论
       >    1. 大部分对象都是朝生夕死的
       >    2. 少部分年级大的对象持有年纪小的对象的引用
       > 2. 分代收集是弱分代理论1，有了分代之后就只需要在新生代进行频繁GC，提高了收集效率
       > 3. 如果没有分代收集，新创建的对象和生命周期很长的对象放在一起，由于程序在不断运行会产生大量垃圾，所以每次回收都需要遍历所有对象，扫描整个堆空间，会严重影响程序效率，而且效率不高

   20. 类加载过程？类加载方式？tomcat如何破坏双亲委派模型？

       > 1. 类加载就是把class文件加载到内存
       > 2. 双亲委派
       >    1. 先交给父加载器加载
       >    2. 加载：类加载器扫描自己的url下的类，转化成路劲，然后加载
       >    3. 验证：
       >    4. 准备：
       >    5. 解析：
       >    6. 初始化：

   21. 什么情况发送ygc？什么情况下发送full gc？

       1. ygc

          > 1. Eden区满了

       2. full gc

          > 

   22. jvm怎么调优？一般从哪里入手？

   23. 为什么有垃圾回收还会发生内存泄漏？

       > java里的内存泄漏是指 已经不用的对象长期占用着内存或者说生命周期短的对象长期在内存中。
       >
       > 内存泄漏原因：1. 一般是对象的作用域设置的不合理
       >
       > ​							2. 不用的对象没有手动置为 null

   24. 内存持续上升？怎么定位问题？

   25. jvm参数？

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

      1. 锁升级？偏向锁->自旋锁->重量级锁

         > 

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

          > 1. ThreadFactory:
          > 2. coreSize:
          > 3. queue:
          > 4. maxSize:
          > 5. RejectPolicy:
          > 6. TimeUnit:
          > 7. time:
       
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
   17. 浏览器输入一个域名发生了什么？

6. spring

   1. 为什么spring不同class来建立bean，而要用beanDefinition？

      > 

   2. bean的生命周期？

   3. bean的scope？

      > 在spring context中只定义了singleton和prototype两个scope
      >
      > 在spring web中新增了request session等scope

   4. 静态代理和动态代理的区别？

      1. 静态代理：

         > 1. 需要硬编码，而且要为每个需要被代理的类生成其实现，扩展性不高 但是性能高

      2. 动态代理：

         > 1. 

   5. jdk动态代理和cglib动态代理的区别？

      1. jdk动态代理
      2. cglib动态代理

   6. spring 默认 用什么代理？

      > spring framework默认使用jdk动态代理
      >
      > 但是spring boot默认使用cglib动态代理

   7. beanFactory和applicationContext那个才是ioc容器？

      > beanFactory: 定义了ioc容器最基础的功能
      >
      > applicationContext：实现了beanFactory接口，不过对于beanFactory的接口方法都是委托给DefaultListableBeanFactory来实现的。除此之外，添加了额外的功能（国际化，事件驱动）。

   8. 三级缓存怎么实现？怎么解决循环依赖

      > 

   9. spring aop实现原理？

   10. spring mvc流程？

   11. spring boot自动装配原理？

   12. spring 事务实现方式？

   13. 事务传播级别？？

   14. 源码？
       1. spring session源码
          1. MapSession.getId(): 产生一个UUID作为session

7. kafka

   1. kafka为什么这么快？

   2. 零拷贝？

      1. 存储消息使用mmap：内核和程序的虚拟地址映射为同一个物理地址
      2. poll消息使用sendFile：数据对应用程序不可见

   3. kafka的日志段如何读写？

   4. kafka的消息是推还是拉，怎么实现的？

      > 由kafkaConsumer自己拉

   5. 怎么保证消息不丢失？

      > 

   6. 如何处理重复消息？

   7. 消息的有序性？

   8. 消息的堆积处理？

   9. kafka的主题与分区内部是如何存储的，有什么特点？

   10. 与传统的消息系统相比，kafka的消费模型有什么优点？

   11. kafka如何实现分布式的数据存储与数据读取？

   12. 索引

       1. 什么是稀疏索引？和密集索引的关系以及区别

          > 

       2. kafka的索引是稀疏索引，主要有三类

          1. 时间戳索引
          2. 位移索引
          3. 事务索引

   13. 源码？

8. mysql

   1. 三大范式？

      > 一范式：所有属性都不可分割
      >
      > 二范式：在一范式的基础上，消除了属性对非住属性的依赖
      >
      > 三范式：在二范式的基础上，消除了传递依赖

   2. 一条select语句的执行过程？

      > 

   3. 一条update sql执行过程？

      1. 连接器：
      2. 分析器：
      3. 优化器：memory存储引擎
      4. 执行器：
      5. innodb：buffer pool

   4. innodb和myisam的区别？

      1. 事务：
      2. 外键：
      3. 索引：inndob存储数据是聚簇索引，主键和数据放在一起。myisam的索引和数据是分开的
      4. 日志：innodb有redo log和undo log
      5. count：
      6. 锁：innodb支持行锁、表锁，而myisam只支持表锁
      7. 存储文件：innodb有两个文件：frm是表定义文件，idb是数据文件。myisam有三个文件：frm是表定义文件，myd是数据文件，myi是索引文件
      8. 插入顺序：innodb插入的数据会按主键排序（会导致页分裂），myisam数据的存放顺序和插入顺序一致
      9. 二级索引：innodb的二级索引存的是主键，myisam的索引存的是数据页的地址

   5. myisam查询为什么比innodb快？

      > 1. myisam的二级索引存储的是数据的地址，不需要回表
      > 2. myisam不支持事务等特性，不需要记录redo log等日志

   6. innodb的特性？

      1. 插入缓冲
      2. 二次写

   7. 为什么自增主键不连续？

   8. 自增主键理解？

   9. 为什么innodb必须要有主键？

      > 1. 

   10. innodb为什么推荐自增id？

       > 1. 如果不是自增，那么数据就有可能从页的中间插入，会频繁导致页分裂，造成性能下降
       >
       > 2. 

   11. 索引
       1. 为什么用b+树
       2. 索引类型？
       3. 索引覆盖？回表？索引下推？联合索引？
       4. 索引失效场景？
       5. 最左匹配原则？
       6. 联合索引建立规则？
       7. 前缀索引？
       8. 百万级别数据怎么删除？
       9. 普通索引和联合索引怎么选择？

   12. 锁

   13. 事务
       1. acid？

       2. mvcc？

          > rr下的mvcc：
          >
          > rc下的mvcc：

   14. explain？

   15. 脏页？怎样刷新脏页？

   16. 一条sql很慢怎么定位？

   17. sql优化？

   18. mysql的三种日志？

   19. 什么是撞库、脱库、洗库？

   20. 主从复制？

   21. 如何分库分表？

   22. 分库分表之后怎么平滑上线？

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

   23. redis怎么删除大key（指某个key的value很大）？

       > 1. 如果线上redis出现大key，不可立即执行del，因为del会造成阻塞，导致其他请求超时->redis连接池耗尽->依赖redis的业务出现异常
       > 2. 解决方案：
       >    1. 系统负载低的时候

   24. 主库挂怎么办》？

10. dubbo

    1. dubbo支持的协议？
    2. dubbo怎么实现熔断？
    3. dubbo调用过程？
    4. 怎么实现限流？
    5. 序列化实现原理？
    6. dubbo怎么支持事务？

11. 系统设计

    1. 如何设计一个高并发系统？
    2. 需求多变时，怎么设计？
    3. 使用hashMap实现一个带时间的缓存？
    4. 如何设计一个秒杀系统？
    5. java接口设计？
    6. 短连接？
    7. 抢红包？
    8. 扫码登陆？
    9. 延迟队列？
    10. 敏感数据过滤设计？
    11. rpc设计？

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
    10. 限流算法：
        1. 漏桶算法
        2. 令牌桶算法
        3. 滑动窗口
        4. 限流库：
           1. 单机限流：guava
           2. 分布式限流： redis+lua

15. linux

    1. tcpdump抓包过程？
       1. 先查看网卡：ip a | grep
       2. tcpdump -i ens192 -nn host 192.168.3.63 port 5533 -Xs0

16. 其他

    1. 怎么跨线程传递THreadLocal

       > 使用阿里的TTL：对ThreadLocal进行store和relay

    2. 生产者消费者模型？（三种方案）

    3. 如果一个Java进程突然消失了，你会怎么去排查这种问题？

       1. linux的OOM killer

          > 该机制会监控那些占用内存过大的进程，为了防止内存耗尽而把该进程杀掉
          >
          > egrep -i 'killed process' /var/log/messages

       2. jvm自身故障

          > jvm发生致命错误导致崩溃时，会生成一个hs_err_pid_xxx.log文件，默认情况在这个文件在工作目录，也可以指定： -XX:ErrorFile=/var/log/hs_err_pid<pid>.log

    4. 出现大量502怎么解决？