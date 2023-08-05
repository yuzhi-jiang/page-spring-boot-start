# page-spring-boot-start
分页插件
这个是基于pagehelper的一个封装了一个注解的starter
@autopage
作用在方法中，可以对方法中的第一个查询sql进行自动分页，如果没有被消费page.start(),则函数执行后自动清除

原理：aop切面,pageheler.start()的原理（基于线程消费分页）
