1. nio

   1. select、poll、epoll的区别

      1. select    ![image-20210914152249696](image-20210914152249696.png)
      2. poll ![image-20210914152318089](image-20210914152318089.png)
      3. epoll ![image-20210914152339830](image-20210914152339830.png)
      
   4. ![image-20210914203322784](image-20210914203322784.png)
   
   5. ![image-20210914203454462](image-20210914203454462.png)
   
   2. 零拷贝

      1. mmap ![image-20210914152522024](image-20210914152522024.png)

         ![image-20210914152538575](image-20210914152538575.png)

      2. sendfile 内存中只有一份文件的拷贝（存放在内核空间），整个过程过对用户空间不可见

   3. buffer

   4. channel
   
   5. selector
   
   7. netty
   
   8. 线程池怎么自定义拒绝策略（）
   
      > 继承RejectExecutionHandler类，重写rejectedExecution 方法
      >
      > 自定义策略：把任务序列化，持久到磁盘（或者数据库）。单点问题可以通过备份来解决
