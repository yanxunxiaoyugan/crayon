1. netty的核心组件

   1. channel：对socket的封装。channel流程图：![image-20211130122924863](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20211130122924863.png)
   2. EventLoop：对java线程的封装
   3. channelHandler：
   4. event：使用回调来处理event事件
   5. future：异步通知
   6. byteBuf：
      1. 通过内置的复合缓冲区实现了透明的零拷贝
      2. 容量可以自动增长
      3. 读写之间不需要像ByteBUffer.flip，，读写使用了不同的索引
      4. 支持池化
      5. 支持链式调用
      6. 支持引用计数
   7. ButeBuf支持三种模式
      1. 堆缓冲区：放在jvm的堆中
      2. 直接缓冲区：
      3. 复合缓冲区：将多个ByteBuf表示为一个ByteBuf

2. Channel、EventLoop、EventLoopGroup的关系：

   1. 一个EventLoopGroup对应一个或多个EventLoop
   2. 一个EventLoop在他的生命周期内只和一个Thread绑定
   3. 所有由EventLoop处理的IO事件都由该EventLoop绑定的Thread来处理
   4. 一个Channel在它的生命周期内只会被一个EventLoop处理
   5. EventLoop可能会处理多个Channel

3. 问题

   1. netty如何支持主从reactor模式
   2. 为什么说netty的main reactor大多不能用到一个线程组
   3. netty给channel分配NIO eventLoop的规则
   4. 为什么需要应用层keepalive
      1. 协议分层，传输层关注的是连接是否可用，应用层关注的是服务是否可用
      2. tcp的keepalive默认关闭，经过中转设备的keepalive包可能被丢弃
      3. keepalive太长，属于系统参数，修改会影响所有应用

   