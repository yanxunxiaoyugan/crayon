1. dubbo整合spring
	
	1. xml模式
	
	> dubbo整合spring的模块有META-INF/spring.handlers, spring.schema,spring.handlers定义了dubbo的入口类DubboNamespaceHandler，然后spring会调用dubboNamespaceHandler的init()方法。 spring.schema定义了一些xsd文件
	>
	> 、
	
	1. annotation模式
	  1. ServiceAnnotationBeanPostProcessor实现了BeanDefinitionRegistryPostProcessor接口，会把扫描到的DubboService注解的类的ServiceBean加入到beanDefinitionMap中。Dubbo的SergviceBean有个属性是ref， ref引用的就是原始bean。ServiceBean是用来暴露bean的。
	
2. 服务暴露流程：https://juejin.cn/post/6844903686884294663

   1. 每个SpringBean都是ServiceBean（implements **ApplicationListener**），订阅了ApplicationEvent时间，onApplicationEvent（）方法暴露每个服务
   2. 判断是否需要延迟暴露，不需要的话直接暴露服务
   3. 校验信息。每个服务可以暴露给多个注册中心，也支持多协议暴露。先遍历暴露协议，在遍历注册中心
   4. 对ServiceBean ref的SpringBean创建代理，创建的代理叫做invoker。
   5. 然后需要把invoker暴露出去，把invoker包装成exporter放到exporterMap里。启动一个Netty服务，把decoder，encoder，nettyHandler放到pipeline里面。nettyHandler就是处理请求的handler
   6. 启动完netty服务后，把服务的信息注册到注册中心

3. 服务调用流程

   1. 从注册中心获取所有的invoker，也就是当前服务所有的provider， 返回List<Invoker>
   1. 对所有invoker，进行路由匹配，就是一堆规则，返回匹配了的invoker
   1. 负载均衡，有不同的策略可以选择，最终选择一个invoker返回
   1. 走filter逻辑，默认8个filter。走完后使用netty发送请求
   1. ======
   1. 请求到达provider端
   1. 默认有三个handler（decoder，encoder，nettyHandler），先decoder对请求进行解码，然后进入nettyHandler
   2. provider维护了一个map（exporterMap<ServiceKey,Exporter>），里面存放了所有的exporter，根据请求拼接出serviceKey，然后找到exporter
   2. 拿到exporter之后，也就拿到了invoker，也就找到了具体的服务类，执行即可
   2. 执行完后，把结果encoder，通过netty返回
   
4. 给你来一份面经
   innodb和myisam有什么区别？

5. MVCC ?

6. mysql页分裂？会有什么影响？

7. 怎么解决OOM？

8. kafka重复消费？怎么防止丢失消息？

9. 