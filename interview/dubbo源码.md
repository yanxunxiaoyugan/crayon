1. dubbo整合spring
	
	1. xml模式
	
	> dubbo整合spring的模块有META-INF/spring.handlers, spring.schema,spring.handlers定义了dubbo的入口类DubboNamespaceHandler，然后spring会调用dubboNamespaceHandler的init()方法。 spring.schema定义了一些xsd文件
	>
	> 、
	
	1. annotation模式
	  1. ServiceAnnotationBeanPostProcessor实现了BeanDefinitionRegistryPostProcessor接口，会把扫描到的DubboService注解的类的ServiceBean加入到beanDefinitionMap中。Dubbo的SergviceBean有个属性是ref， ref引用的就是原始bean。ServiceBean是用来暴露bean的。
	
2. 服务暴露流程

  1. sadf https://juejin.cn/post/6844903686884294663

3. 服务调用流程

  1. provider维护了一个map（exporterMap<ServiceKey,Exporter>），里面存放了所有的exporter，根据请求拼接出serviceKey，然后找到exporter