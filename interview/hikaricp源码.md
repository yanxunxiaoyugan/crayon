1. 获取连接时序图：

2.  ``` 

3. ```java
   //可用连接同步器, 用于线程间空闲连接数的通知, synchronizer.currentSequence()方法可以获取当前数量
   //其实就是一个计数器, 连接池中创建了一个连接或者还回了一个连接就 + 1, 但是连接池的连接被借走, 是不会 -1 的, 只加不减
   //用于在线程从连接池中获取连接时, 查询是否有空闲连接添加到连接池, 详见borrow方法
   private final QueuedSequenceSynchronizer synchronizer;
   //sharedList保存了所有的连接
   private final CopyOnWriteArrayList<T> sharedList;
   //threadList可能会保存sharedList中连接的引用
   private final ThreadLocal<List<Object>> threadList;
   //对HikariPool的引用, 用于请求创建新连接
   private final IBagStateListener listener;
   //当前等待获取连接的线程数
   private final AtomicInteger waiters;
   //标记连接池是否关闭的状态
   private volatile boolean closed;
   
   
   /**
    * 该方法会从连接池中获取连接, 如果没有连接可用, 会一直等待timeout超时
    *
    * @param timeout  超时时间
    * @param timeUnit 时间单位
    * @return a borrowed instance from the bag or null if a timeout occurs
    * @throws InterruptedException if interrupted while waiting
    */
   public T borrow(long timeout, final TimeUnit timeUnit) throws InterruptedException {
      //①
      //先尝试从ThreadLocal中获取
      List<Object> list = threadList.get();
      if (weakThreadLocals && list == null) {
         //如果ThreadLocal是 null, 就初始化, 防止后面 npe
         list = new ArrayList<>(16);
         threadList.set(list);
      }
      //②
      //如果ThreadLocal中有连接的话, 就遍历, 尝试获取
      //从后往前反向遍历是有好处的, 因为最后一次使用的连接, 空闲的可能性比较大, 之前的连接可能会被其他线程偷窃走了
      for (int i = list.size() - 1; i >= 0; i--) {
         final Object entry = list.remove(i);
         @SuppressWarnings("unchecked") final T bagEntry = weakThreadLocals ? ((WeakReference<T>) entry).get() : (T) entry;
         if (bagEntry != null && bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
            return bagEntry;
         }
      }
      //③
      //如果没有从ThreadLocal中获取到连接, 那么就sharedList连接池中遍历, 获取连接, timeout时间后超时
      //因为ThreadLocal中保存的连接是当前线程使用过的, 才会在ThreadLocal中保留引用, 连接池中可能还有其他空闲的连接, 所以要遍历连接池
      //看一下requite(final T bagEntry)方法的实现, 还回去的连接放到了ThreadLocal中
      timeout = timeUnit.toNanos(timeout);
      Future<Boolean> addItemFuture = null;
      //记录从连接池获取连接的开始时间, 后面用
      final long startScan = System.nanoTime();
      final long originTimeout = timeout;
      long startSeq;
      //将等待连接的线程计数器加 1
      waiters.incrementAndGet();
      try {
         do {
            // scan the shared list
            do {
               //④
               //当前连接池中的连接数, 在连接池中添加新连接的时候, 该值会增加
               startSeq = synchronizer.currentSequence();
               for (T bagEntry : sharedList) {
                  if (bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)) {
                     // if we might have stolen another thread's new connection, restart the add...
                     //⑤
                     //如果waiters大于 1, 说明除了当前线程之外, 还有其他线程在等待空闲连接
                     //这里, 当前线程的addItemFuture是 null, 说明自己没有请求创建新连接, 但是拿到了连接, 这就说明是拿到了其他线程请求创建的连接, 这就是所谓的偷窃了其他线程的连接, 然后当前线程请求创建一个新连接, 补偿给其他线程
                     if (waiters.get() > 1 && addItemFuture == null) {
                        //提交一个异步添加新连接的任务
                        listener.addBagItem();
                     }
                     return bagEntry;
                  }
               }
            } while (startSeq < synchronizer.currentSequence()); //如果连接池中的空闲连接数量比循环之前多了, 说明有新连接加入, 继续循环获取
            //⑥
            //循环完一遍连接池(也可能循环多次, 如果正好在第一次循环完连接池后有新连接加入, 那么会继续循环), 还是没有能拿到空闲连接, 就请求创建新的连接
            if (addItemFuture == null || addItemFuture.isDone()) {
               addItemFuture = listener.addBagItem();
            }
            //计算 剩余的超时时间 = 用户设置的connectionTimeout - (系统当前时间 - 开始获取连接的时间_代码①处 即从连接池中获取连接一共使用的时间)
            timeout = originTimeout - (System.nanoTime() - startScan);
         } while (timeout > 10_000L && synchronizer.waitUntilSequenceExceeded(startSeq, timeout)); //③
         //⑦
         //这里的循环条件比较复杂
         //1. 如果剩余的超时时间, 大于10_000纳秒
         //2. startSeq的数量, 即空闲连接数超过循环之前的数量
         //3. 没有超过超时时间timeout
         //满足以上 3 个条件才会继续循环, 否则阻塞线程, 直到满足以上条件
         //如果一直等到timeout超时时间用完都没有满足条件, 结束阻塞, 往下走
         //有可能会动态改变的条件, 只有startSeq数量改变, 是②处添加的创建连接请求
      } finally {
         waiters.decrementAndGet();
      }
   
      return null;
   }
   
   /**
    * 该方法将借出去的连接还回到连接池中
    * 不通过该方法还回的连接会造成内存泄露
    *
    * @param bagEntry the value to return to the bag
    * @throws NullPointerException  if value is null
    * @throws IllegalStateException if the requited value was not borrowed from the bag
    */
   public void requite(final T bagEntry) {
      //⑧
      //lazySet方法不能保证连接会立刻被设置成可用状态, 这是个延迟方法
      //这是一种优化, 如果要立即生效的话, 可能会需要使用volatile等, 让其他线程立即发现, 这会降低性能, 使用lazySet浪费不了多少时间, 但是不会浪费性能
      bagEntry.lazySet(STATE_NOT_IN_USE);
   
      //⑨
      //将连接放回到threadLocal中
      final List<Object> threadLocalList = threadList.get();
      if (threadLocalList != null) {
         threadLocalList.add(weakThreadLocals ? new WeakReference<>(bagEntry) : bagEntry);
      }
      //通知等待线程, 有可用连接
      synchronizer.signal();
   }
   
   /**
    * 在连接池中添加一个连接
    * 新连接都是添加到sharedList中, threadList是sharedList中的部分连接的引用
    *
    * @param bagEntry an object to add to the bag
    */
   public void add(final T bagEntry) {
      if (closed) {
         LOGGER.info("ConcurrentBag has been closed, ignoring add()");
         throw new IllegalStateException("ConcurrentBag has been closed, ignoring add()");
      }
      //⑩
      sharedList.add(bagEntry);
      synchronizer.signal();
   }
   
   /**
    * 从连接池中移除一个连接.
    * 这个方法只能用于从<code>borrow(long, TimeUnit)</code> 或者 <code>reserve(T)</code>方法中获取到的连接
    * 也就是说, 这个方法只能移除处于使用中和保留状态的连接
    *
    * @param bagEntry the value to remove
    * @return true if the entry was removed, false otherwise
    * @throws IllegalStateException if an attempt is made to remove an object
    *                               from the bag that was not borrowed or reserved first
    */
   public boolean remove(final T bagEntry) {
      //⑪
      //尝试标记移除使用中和保留状态的连接, 如果标记失败, 就是空闲的连接, 直接返回 false
      //也就是检查连接的状态, 不能移除空闲的连接或者已经标记移除的连接
      if (!bagEntry.compareAndSet(STATE_IN_USE, STATE_REMOVED) && !bagEntry.compareAndSet(STATE_RESERVED, STATE_REMOVED) && !closed) {
         LOGGER.warn("Attempt to remove an object from the bag that was not borrowed or reserved: {}", bagEntry);
         return false;
      }
      //如果上面标记成功了, 那么从连接池中移除这个连接
      final boolean removed = sharedList.remove(bagEntry);
      if (!removed && !closed) {
         LOGGER.warn("Attempt to remove an object from the bag that does not exist: {}", bagEntry);
      }
   
      // synchronizer.signal();
      return removed;
   }
   ```