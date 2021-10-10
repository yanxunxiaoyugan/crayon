# kafka

1. 为什么kafka不支持读写分离

   1. 读写分离试用于读多写少的场景、而且会带来不一致的问题

   ![image-20210914202842794](image-20210914202842794.png)

2. 流和生产者、消费者的区别

3. kafka的topic和分区

   1. isr、osr

      > topic的分区leader负责维护和跟踪ISR集合中所有follower副本的滞后状态，当follower落后太多时，分区leader会把它从ISR中移除。如果OSR有follower追上来了，分区leader会把它从OSR转移到ISR

   2. HW、LEO、ISR的联系

      > ISR：和分区leader保持同步的folloer集合
      >
      > LEO: log end offset,是一个分区下一个即将写入消息的offset
      >
      > HW：ISR中最小的LEO就是整个分区的HW

   

   > 流是一组从生产者到消费者的数据

4. kafka的消费者把每个分区最后读取的偏移量储存在哪里？

   > 之前把偏移量存储在kafka或者zookeeper中，现在把偏移量存储在_consumer_offset的topic中，这个topic有50个分区

5. broker和集群

   > 1. broker是集群的组成部分，每个集群有一个broker叫做controller，负责这个集群的管理工作，包括将分区分配给broker和监控broker
   > 2. 集群中的每个partition都有一个leader，当leader宕机后，其他ISR可以成为leader，之后生产者和消费者都要重新连接到新的leader
   > 3. 集群的好处：数据类型分离，安全需求隔离，多数据中心

6. 保留消息

   > kafka的默认保留策略：要么保留一段时间，要么保留到消息达到一定大小（10GB）

7. producer

   1. kafka的消息变量

      > String topic
      >
      > Intger partition
      >
      > Headers headers
      >
      > K key
      >
      > V value (value一般不为空，，为空就是墓碑消息)
      >
      > Long timestamp

   2. kafka producer整体架构，kafkaProducer是线程安全的

      ![image-20210929194309950](image-20210929194309950.png)

   3. 

   4. 参数

      > 1. ack all（所有的isr）
      > 2. buffer.memory：recordAccumulator的大小，默认为32MB
      > 3. 压缩 lz4 zstrand
      > 4. retries，重试可能造成重复发送，需要consumer去重处理。只针对于可重试异常，如果重试次数用完之后，还没有写入kafka broker，那么抛出异常
      > 5. retry.backoff.ms: 两次重试的停顿时间，防止频繁重试，默认100ms
      > 6. batch.size： producer会将发往同一个partition的多条消息封装到一个batch，当batch满了，producer的io线程会发送batch中的所有消息。默认为16k，这个值设置的太小，会影响吞吐量，太大会给内存带来压力
      > 7. linger.ms: 当batch.size没有被填满时，当过了linger.ms时间过后，batch还没有填满，那么io线程会把batch里的消息发送出去。默认值是0，不关心batch是否填满，这样会拉低吞吐量
      > 8. max.request.size: producer能够发送的最大消息大小,默认1mb。和broker的message.max.bytes相关联。
      > 9. request.timeout.ms: producer发送消息给broker后，broker需要在规定的时间给出响应。如果超过了这个时间没有收到broker的响应，会在回调函数中抛出异常
      > 10. connections.max.idle.ms: 多久关闭连接，默认9分钟
      > 11. max.in.flight.request.per.connection = 1,producer可以确保某一时刻只能发送一个请求，可以保证顺序发送
      > 12. send.buffer.bytes： socker的发送缓冲区的大小
      > 13. max.block.ms: 如果生产者的生产速度超过了sender线程的发送速度，会导致recordAccumulator空间不足。这是producer调用send方法要么阻塞要么抛出异常，阻塞的最大时间就是此配置

   5. 注意：

      1. 主线程发过来的消息都会被追加到RecordAccumulator的某个Deque中，RecordAccumulator会为每个分区维护一个Deque，队列的内容就是ProducerBatch,即Deque<ProducerBatch>

      2. 消息在网络上都是以Byte的形式传输的，在发送之前需要创建一块内存区域来保存消息，kafkaProducer使用ByuteBuffer实现消息内存的创建和释放。但是频繁的创建和释放是比较消耗资源的，在RecordAccumulator的内部有一个Buffer Pool来实现对ByteBuffer的复用。不过BUffer Pool只针对特定大小的ByetBuffer进行管理，其他大小的ByteBuffer不会缓存进ByteBuffer，这个特定的大小由batch.size指定，默认16k

      3. 当一条消息（ProducerRecord）流入RecordAccumulator时，先寻找消息分区对应的Deuqe（如果没有就新建），再从Deque的尾部获取一个ProducerBatch，查看ProducerBatch中是否还有空间写入这个ProdcuerRecord，如果可以就写入。如果不行就新建一个ProducerBatch，如果当前消息小于batch.size，就以batch.size创建ProducerBatch，这个batch可以通过BufferPool来管理，这个batch是可以被复用的。如果超过了，就创建消息的大小的创建ProducerBatch，这个batch无法复用。

      4. sender线程从RecordAccumulator中获取缓存的消息之后，会把消息转换由<Partition,Deque<ProducerBatch>>转换成<Node,List<ProducerBatch>>,Nodo表示kafka的Broker节点（Node都是分区leader）。 kakfaProducer不管理消息属于哪个分区，直接向建立连接的broker发送消息。

      5. sender线程还会把<Node,List<ProducerBatch>>，转换成<Node,Request>的形式

      6. 请求在sender线程发往kafka broker之前还会保存到inFlightRequests中，具体形式为Map<NodeId,Deque<Request>>,主要作用是缓存已经发出去但是还没有收到响应的请求。除此之外，InFlightRequests还可以通过配置参数来限制每个连接（kafkaProducer和Node的连接）最多缓存的请求数，默认值为5（每个连接最多只能缓存5个未响应的请求），超过该值，无法向该node发送消息。通过比较Deque的size和配置的参数来判断消息是否堆积了很多未响应的消息。除此之外，InFlightRequests可以获得leastLoadedNode（node中负载最小的一个），Deque还未 确认消息数最小的就是leastLoadedNode

      7. 元数据

         > 1. kafka集群有哪些主题，主题有多少分区，分区有多少follower，follower在哪些节点，哪些follower是ISR、ar，有多少broker
         > 2. 如果消息没有指定主题信息或者超过metadata.max.age.ms（默认5分区），会引起元数据的更新（由sender线程负责更新）

         

   6. producer无消息丢失配置。

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

8. kafka 的consumer

   1. kafka的consumer不是线程安全的，consumer会把每个partition的offset提交到__consumer_offset

   2. consumerGroup的分区策略

   3. kafkaConsumer.assign(List<TopicPartition> partition):可以订阅topic中的某个分区

   4. rebalance

      > 1. rebalance规定了一个consumer group下的consumer如何达成一致来分配订阅某个topic的所有分区
      > 2. 

   5. consumer.subscribe(list<String> ) 方法不是增量，而是覆盖

   6. consumer的参数

      > * session.timeout.ms: consumer group检测组内成员发送崩溃的时间
      > * max.poll.interval.ms: consumer处理逻辑的最大时间。如果consusmer的逻辑处理时间超过了此值，coordinator会把该consumer踢出改组，然后对该组进行Rebalance
      > * auto.offset.reset: 如果group没有提交offset到broker的topic（__consumer_topic），那么就会从头消费
      > * enable.auto.commit: 是否自动提交offset
      > * fetch.max.bytes: consumer一次最大获取的字节数，如果业务数据大于此值，那么consumer无法消费这个消息
      > * max.poll.records: 单次poll返回的最大消息数
      > * heartbeat.interval.ms: 该值必须小于session.timeout.ms。每个consumer都会根据此值周期性的向group coordinator发送heartbeat，然后groupcoordinator给各个consumer响应。如果group coordinator给sonsumer的响应包好了 REBALANCE_IN_PROGRESS标识，各个consumer就知道已经发送了rebalance
      > * connection.max.idle.ms: kakfa会周期性的关闭空闲连接

   7. kafka的consumer是有两个线程，一个是用户主线程，用来poll、rebalance，位移提交，异步任务结果的处理，，还有一个是心跳线程

   8. consumer的位移

      1. 常见的交付语义

         > 1. at most once: 消息可能丢失，但不会重复处理
         > 2. at least once：消息不会丢失，但是会重复消费
         > 3. exactly once：消息不会丢失并且只会被消费一次
         >
         > 如果consumer在消费之前（poll消息之后）提交offset，consumer崩溃会导致消息丢失
         >
         > 如果consumer在消费之后提交offset，consumer崩溃来不及提交offset，会导致消息重复消费
         
      2. 使用commitAsync提交offset时，如果设置了retry也有可能造成重复消费。我们可以维护一个递增的序号来维护异步提交的顺序

   9. consum控制或关闭消费

      1. kafka使用pause和resume来暂停和恢复

   10. rebalance触发条件

          1. 组成员发送变化：加入新的consumer，consumer下线、崩溃
          2. 组订阅的topic数发生变化：比如使用正则订阅topic
          3. topic的分区数发生变化： topic增加的分区

   11. 分区策略

          1. round-robin
          2. range
          3. sticky

9. topic和partition

   1. 

10. 持久化

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

