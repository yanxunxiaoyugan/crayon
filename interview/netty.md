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

   