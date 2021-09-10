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

   > 1. string:
   > 2. list
   > 3. hash
   > 4. set
   > 5. zset

4. redis为什么快

   > 1. 基于内存
   > 2. 支持多种数据类型，计算向数据移动
   > 3. 单线程，减少上下文的切换
   > 4. io多路复用 epoll reactor

5. 缓存穿透、击穿、雪崩

   > 1. 穿透： 查询一个缓存中不存在、数据库也不存在的key
   >    1. 缓存过期时间很短的空值：消耗内存、如果数据库中添加了这个key，会导致数据不一致
   >    2. bloom过滤器：维护困难，如果key是持续不断加入的，过滤器总会被打满
   > 2. 击穿：查询缓存不存在，数据库存在的key
   >    1. 加锁排队：当获取到了锁之后，可以短暂sleep，然后再去查询缓存，如果还是没有则重建缓存，获取不到锁的线程直接返回或者阻塞（等获取锁的线程重建缓存之后唤醒）。
   >    2. 
   > 3. 雪崩：缓存中的key大量失效
   >    1. 预防措施：给key设置随机过期时间、永不过期（不设置过期时间、当发现缓存快过期了自动更新缓存）
   >    2. 已经发生的措施：加锁排队

6. redis 的持久化

   1. 为什么需要持久化：

      > 把内存的数据写入到磁盘，防止服务宕机内存数据丢失。

   2. RDB（redis database）：把内存的数据以快照的方式写到磁盘，save seconds changeKeyCount来指定自动持久化的频率。

   3. AOF(append only file)：以默认的频率把执行的命令写入到文件，fsync刷盘

   4. 对比

      > 1. aof比rdb更新频率高，所以aof比rdb文件更大
      > 2. rdb恢复数据更快
      > 3. 优先加载aof

7. redis的淘汰策略

   > 1. noeviction： 不删除策略
   > 2. allKeys-lru：删除最近最少使用的key
   > 3. volatile-lru：只删除最近最少使用并且设置了expire的key
   > 4. allKey-random：随机删除
   > 5. volatile-random：随机删除设置了expire的key
   > 6. volatile-ttl：限于expire的key，优先删除ttl短的key

8. redis的删除策略

   > 1. 被动删除，当一个key已经过期时，会触发惰性删除策略
   > 2. 主动删除：惰性删除无法保证冷数据被及时清除，redis会定期主动淘汰一批过期key
   > 3. maxmemory删除：当redis使用内存到达maxmemory时，会使用淘汰策略 淘汰key

9. 事务

   > 1. redis

10. 复制

11. 主从

12. 哨兵

13. 集群

14. 先更新数据还是先更新缓存

15. redisson、lettuce

















# jvm





# jdk



# mysql



# spring 

# mq 



# 网络

tcp的顺序性

# 分布式

