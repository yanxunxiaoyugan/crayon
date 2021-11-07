1. 注册中心
   1. 注册中心的作用
   
      1. 动态发现
      2. 动态加入
   
   2. 缓存机制
   
      1. 缓存就是用空间换时间，如果每次远程调用都需要去注册中心获取服务列表，会让注册中心承受巨大压力，整个系统的性能也会降低。
      2. 消费者或服务治理中心获取注册信息后，会在本地缓存一份（concurrentHashMap），磁盘上也会持久化一份文件。
      3. 服务初始化的时候，AbstractRegistry会从本地磁盘文件读取数据到properties中，并放到concurrentHashMap中
   
   3. 重试机制
   
      > FailbackRegistry抽象类中定义了一个ScheduledExecutorService,每经过固定间隔(默认为5秒)调用FailbackRegistry#retry()方法
   
2. dubbo的扩展点

   1. spi

      > spi是Java提供的一种服务接口与服务实现分离的机制，然后服务调用者选择自己需要的外部实现。服务提供者只提供接口，服务调用者自己选择实现

   2. dubbo的spi(service provider interface)

      > 1. jdk的标准spi会一次性实例化扩展点的所有实现，如果没有用到页加载，浪费资源
      > 2. 增加了ioc和aop的支持
      > 3. 如果扩展点加载失败，扩展点名称都获取不到。异常信息会被吞掉

3. dubbo启停原理

4. dubbo远程调用

5. dubbo容错原理

6. 

7. 复习
   1. 负载均衡
      1. 随机
      2. 轮训
      3. 最少活跃请求数
      4. 一致性hash
   2. spi机制
   3. 分布式锁

