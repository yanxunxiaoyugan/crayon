# redis

1. 对redis 的理解

   > remote dictartionary server(远程字典服务)，支持多个数据类型，基于内存的非关系型数据库

2. redis的使用场景

   > 1. 缓存
   > 2. 数据库
   >    1. 排行榜（zset）
   >    2. 计数器（播放量，在线人数）（原子自增）
   >    3. 好友关系
   >    4. 简单消息队列
   >    5. 共享session
   >    6. 限流
   >    7. 分布式锁
   > 3. 不适用的场景：访问频率低、数据量大

3. redis的数据类型

   > 1. string: int embstr 
   > 2. list
   > 3. hash
   > 4. set
   > 5. zset

4. redis为什么快

   > 1. 基于内存
   > 2. 支持多种数据类型，计算向数据移动
   > 3. 单线程，减少上下文的切换
   > 4. io多路复用 epoll reactor

5. redis扩容条件

   > 1. hash表中保存的key数量超过了hash表的大小
   > 2. 当前没有子进程执行aof重写或者生产rdb文件
   > 3. 如果保存的节点数与哈希表大小的比例超过了安全阈值（默认值为5），也会进行扩容

6. redis的hash冲突

   > redis使用链式hash解决冲突。
   >
   > redis使用两个全局hash表，用于rehash操作。当数据库中的数据越来越多时，会触发reahsh操作
   >
   >  	1. 给hash[2]分配hash[1]两倍的空间
   >  	2. 将hash1的数据重新hash到hash2中
   >  	3. 释放hash1的空间
   >
   > rehash具体执行步骤
   >
   > 	1. 给hash[2]分配hash[1]两倍的空间
   > 	2. 在字典中维持一个计数器rehashidx，设置值为0
   > 	3. 在rehash进行期间，每次对字典crud操作时，程序都会将hash1【rehashIdx】 rehash到hash2。rehash执行完后，将redisIdx加1
   >
   > 在rehash执行期间，新加的key-value会被保存到新的hash表中，使用delete，find，set等操作回同时查两张hash表
   >
   > rehash会使得redis内存突增，在满容状态下回导致大量key被驱逐

7. 缓存穿透、击穿、雪崩

   > 1. 穿透： 查询一个缓存中不存在、数据库也不存在的key
   >    1. 缓存过期时间很短的空值：消耗内存、如果数据库中添加了这个key，会导致数据不一致
   >    2. bloom过滤器：维护困难，如果key是持续不断加入的，过滤器总会被打满
   > 2. 击穿：查询缓存不存在，数据库存在的key
   >    1. 加锁排队：当获取到了锁之后，可以短暂sleep，然后再去查询缓存，如果还是没有则重建缓存，获取不到锁的线程直接返回或者阻塞（等获取锁的线程重建缓存之后唤醒）。
   >    2. 
   > 3. 雪崩：缓存中的key大量失效
   >    1. 预防措施：给key设置随机过期时间、永不过期（不设置过期时间、当发现缓存快过期了自动更新缓存）
   >    2. 已经发生的措施：加锁排队

8. redis 的持久化

   1. 为什么需要持久化：

      > 把内存的数据写入到磁盘，防止服务宕机内存数据丢失。

   2. RDB（redis database）：把内存的数据以快照的方式写到磁盘，save seconds changeKeyCount来指定自动持久化的频率。

   3. AOF(append only file)：以默认的频率把执行的命令写入到文件，fsync刷盘

   4. 对比

      > 1. aof比rdb更新频率高，所以aof比rdb文件更大
      > 2. rdb恢复数据更快
      > 3. 优先加载aof

9. redis的淘汰策略

   > 1. noeviction： 不删除策略
   > 2. allKeys-lru：删除最近最少使用的key
   > 3. volatile-lru：只删除最近最少使用并且设置了expire的key
   > 4. allKey-random：随机删除
   > 5. volatile-random：随机删除设置了expire的key
   > 6. volatile-ttl：限于expire的key，优先删除ttl短的key

10. redis的删除策略

   > 1. 惰性过期：当访问一个key时，才会去判断是否过期
   > 2. 定时过期：。每个设置过期时间的key都需要创建一个定时器，到期就会定时删除
   > 3. 定期过期：定时扫描数据库中expire字典中一定数量的key。可以调整时间间隔和扫描的耗时
   > 4. maxmemory删除：当redis使用内存到达maxmemory时，会使用淘汰策略 淘汰key
   > 5. 总结：**redis同时使用了惰性过期和定期过期策略**

11. 事务

   > 1. redis使用mult，exec，watch等命令实现事务
   >
   >    1. multi：开启事务
   >    2. exec：执行事务
   >    3. discard：取消事务
   >    4. watch：监视key，如果事务执行之前。key被其他命令改动，事务将被打断
   >
   > 2. 事务失败场景：
   >
   >    1. 语法错误（参数个数错误，不支持的命令）
   >    2. 命令执行失败（对string执行lpush等操作）
   >    3. watch
   >
   > 3. 为什么不支持回滚：
   >
   >    ​	有当被调用的Redis命令有语法错误时，这条命令才会执行失败（在将这个命令放入事务队列期间，Redis能够发现此类问题），或者对某个键执行不符合其数据类型的操作：实际上，这就意味着只有程序错误才会导致Redis命令执行失败，这种错误很有可能在程序开发期间发现，一般很少在生产环境发现。支持事务回滚能力会导致设计复杂，这与Redis的初衷相违背，Redis的设计目标是功能简化及确保更快的运行速度。
   >
   > 4. 

11. 复制

    1. 复制参数
       1. repl_disable_tcp_nodelay: 控制是否关闭TCP_NODELAY: 关闭时，主节点产生的命令无论大小都会发送给从节点，延迟变小但增加了网络带宽的负担，适合同机架或同机房。打开时，主节点会合并较小的tcp数据包从而节省带宽，默认发送间隔为 40ms，适合网络环境复杂或跨机房部署。
    2. 拓扑
       1. 一主一从
          1. 当应用写命令并发量高并且需要持久化时，可只在从节点开启AOF。不过当主节点脱机时，需要先断开从机。
       2. 一主多从
       3. 树状主从
    3. 复制过程
       1. 保存master信息：执行完slaveof后从节点只保存主节点的地址信息便返回
       2. 建立socker连接：如果无法建立连接，那么会无限重试
       3. 发送ping命令：建立连接后从节点发送ping请求进行首次通信，目的是为了检测主从之间网络套接字是否可用、检测主节点是否可接受处理命令。如果发送ping命令后，从节点没有收到主节点的pong回复或者超时，从节点会断开连接。等待下次重新连接
       4. 权限校验：如果设置了requirepass参数，需要验证密码。如果不通过，复制将终止，从节点将再次发起复制请求
       5. 同步数据：主从复制连接正常通信后，对于首次复制的场景，主节点会生成RDB文件，然后把RDB文件发给从节点。如果从节点短暂断开重连，REDIS在2.8之后的版本使用新的复制命令psync（runId, offset）进行数据同步
       6. 命令持续复制
    4. 读写分离问题
       1. 复制数据延迟：监听主从节点的复制偏移量
       2. 读到过期数据
       3. 从节点故障

    > 2.8全量复制：

    > 2.8后断点续传复制：当从节点再次连上主节点之后，主节点会补发丢失的数据给从节点可以有效避免全量复制带来的开销
    >
    > 1. 主从复制各节点的复制偏移量：参与复制的主从节点都会维护自身复制偏移量。master在处理完写入命令之后，会把命令的字节长度做累加，存储在info relication的master_repl_offset中。slave节点也会上报自身的复制偏移量给主节点，主节点也会把从节点的复制偏移量保存起来，存储在info replication的slave_{id}：offset中。slave节点收到master发送的命令之后，也会累加自身偏移量，存储在info replication的slave_repl_offset中
    > 2. 主节点复制积压缓冲区：保存在master节点的一个固定长度的队列，默认大小为1MB。当master有slave时被创建，这时master响应写命令时，不仅把命令发给从节点，还会写入复制积压缓冲区
    > 3. 主节点运行id：当master重启之后id会发生变化，只要slave监听到masterid发生变化，将进行全量复制。使用debug reload‘命令重新加载EDB文件可以保持运行ID不变

    1. 

12. 主从

    1. 主从同步怎么实现的？

13. 哨兵

    > 选举master过程

14. 集群

    1. 怎么实现数据分片
    2. 怎么做故障转移和发现

15. 先更新数据还是先更新缓存

16. redisson、lettuce

17. 性能优化

    > 1. master不做任务持久化工作，slave可以开启持久化
    > 2. master和slave在同一个局域网
    > 3. 主从复制采用单链表结构，而不是图状结构
    
18. 复习

    1. 单线程模型
    2. 持久化（aof、rdb）
    3. rewrite
    4. 主从
    5. 哨兵
    6. 集群
    7. 缓存雪崩、穿透、击穿
    8. 一致性hash
    9. boolm filter
    10. redis如果list比较大 怎么优化

















# jvm





# jdk



# mysql



# spring 

# mq 



# 网络

tcp的顺序性

# 分布式

