1. dubbo整合spring
	> dubbo整合spring的模块有META-INF/spring.handlers, spring.schema,spring.handlers定义了dubbo的入口类DubboNamespaceHandler，然后spring会调用dubboNamespaceHandler的init()方法。 spring.schema定义了一些xsd文件