# kafka

1. 为什么kafka不支持读写分离

   ![image-20210914202842794](image-20210914202842794.png)

2. 流和生产者、消费者的区别

   > 流是一组从生产者到消费者的数据

3. kafka的消费者把每个分区最后读取的偏移量储存在哪里？

   > 之前把偏移量存储在kafka或者zookeeper中，现在把偏移量存储在_consumer_offset的topic中，这个topic有50个分区
   
4. broker和集群

   > 1. broker是集群的组成部分，每个集群有一个broker叫做controller，负责这个集群的管理工作，包括将分区分配给broker和监控broker
   > 2. 集群中的每个partition都有一个leader，当leader宕机后，其他ISR可以成为leader，之后生产者和消费者都要重新连接到新的leader
   > 3. 集群的好处：数据类型分离，安全需求隔离，多数据中心

5. 保留消息

   > kafka的默认保留策略：要么保留一段时间，要么保留到消息达到一定大小（10GB）
   
6. producer

   1. 参数

      > 1. ack all（所有的isr）
      > 2. buffer.memory
      > 3. 压缩 lz4 zstrand
      > 4. retries，重试可能造成重复发送，需要consumer去重处理
      > 5. retry.backoff.ms: 两次重试的停顿时间，防止频繁重试，默认100ms
      > 6. batch.size： producer会将发往同一个partition的多条消息封装到一个batch，当batch满了，producer的io线程会发送batch中的所有消息。默认为16k，这个值设置的太小，会影响吞吐量，太大会给内存带来压力
      > 7. linger.ms: 当batch.size没有被填满时，当过了linger.ms时间过后，batch还没有填满，那么io线程会把batch里的消息发送出去。默认值是0，不关心batch是否填满，这样会拉低吞吐量
      > 8. max.request.size: producer能够发送的最大消息大小
      > 9. request.timeout.ms: producer发送消息给broker后，broker需要在规定的时间给出响应。如果超过了这个时间没有收到broker的响应，会在回调函数中抛出异常
      > 10. max.in.flight.request.per.connection = 1,producer可以确保某一时刻只能发送一个请求，可以保证顺序发送

   2. producer无消息丢失配置

      > * block.on.buffer.full = true 想当于 max.block.ms.当内存缓冲区满了后，没超过此配置的时间producer会停止接受新消息，等待的时间超过此配置会抛出异常
      > * acks=all or -1 isr全部写入成功
      > * retries = Integer.MAX_VALUE
      > * max.in.flight.request.per.connection = 1
      > * 使用带回调的send发送消息
      > * CallBack中显式的立即关闭producer，即使用close(0) 为了处理消息乱序问题
      > * unclean.leader.election.enable = false 不允许非isr的broker成为leader
      > * replication.factor = 3  3个备份
      > * min.insync.replicas =2 控制某消息至少写入到多少个isr才算成功，只有ack=all这个参数才有效
      > * replication.factor > min.insync.replicas
      > * enable.auto.commit = false 取消自动提交

7. kafka 的consumer

   1. consumer会把每个partition的offset提交到__consumer_offset

   2. rebalance

      > 1. rebalance规定了一个consumer group下的consumer如何达成一致来分配订阅某个topic的所有分区
      > 2. 

   3. consumer.subscribe(list<String> ) 方法不是增量，而是覆盖

   4. consumer的参数

      > * session.timeout.ms: consumer group检测组内成员发送崩溃的时间
      > * max.poll.interval.ms: consumer处理逻辑的最大时间。如果consusmer的逻辑处理时间超过了此值，coordinator会把该consumer踢出改组，然后对该组进行Rebalance
      > * auto.offset.reset: 如果group没有提交offset到broker的topic（__consumer_topic），那么就会从头消费
      > * enable.auto.commit: 是否自动提交offset
      > * fetch.max.bytes: consumer一次最大获取的字节数，如果业务数据大于此值，那么consumer无法消费这个消息
      > * max.poll.records: 单次poll返回的最大消息数
      > * heartbeat.interval.ms: 该值必须小于session.timeout.ms。每个consumer都会根据此值周期性的向group coordinator发送heartbeat，然后groupcoordinator给各个consumer响应。如果group coordinator给sonsumer的响应包好了 REBALANCE_IN_PROGRESS标识，各个consumer就知道已经发送了rebalance
      > * connection.max.idle.ms: kakfa会周期性的关闭空闲连接
      
   5. kafka的consumer是有两个线程，一个是用户主线程，用来poll、rebalance，位移提交，异步任务结果的处理，，还有一个是心跳线程

   6. consumer的位移

      1. 常见的交付语义

         > 1. at most once: 消息可能丢失，但不会重复处理
         > 2. at least once：消息不会丢失，但是会重复消费
         > 3. exactly once：消息不会丢失并且只会被消费一次
         >
         > 如果consumer在消费之前（poll消息之后）提交offset，consumer崩溃会导致消息丢失
         >
         > 如果consumer在消费之后提交offset，consumer崩溃来不及提交offset，会导致消息重复消费

   7. rebalance触发条件

      1. 组成员发送变化：加入新的consumer，consumer下线、崩溃
      2. 组订阅的topic数发生变化：比如使用正则订阅topic
      3. topic的分区数发生变化： topic增加的分区

   8. 分区策略

      1. round-robin
      2. range
      3. sticky

8. 持久化

   1. RDB：

      > bgsave可以在客户端主动触发，也可以又定时任务触发，也可以在slave同步时触发
      >
      > 每次RDB文件都是替换的
      >
      > redis会压缩RDB文件，使用LZF算法
      >
      > 优点：文件紧凑，适合备份，恢复数据时快
      >
      > 缺点：无法秒级持久化，导致和redis数据有较大延迟、版本不兼容

   2. aof

      > 

