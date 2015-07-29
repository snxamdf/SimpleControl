# SimpleControl
* 简单的基于servlet,编写的java web 控制层类似spring ，controller层和事务配置，注解配置。 
* 
* 工作中业余学习。 
* 
* 封装表单提交数据,可以对bean对像或list集合请求数据封装。
* 封装上传文件对像
* 自动装配service层，dao层对像。 
* 返回ajax数据和普通jsp页面 
* 在service层可以开启事务，添加事务注解
* 
* 说明:
* Controller配置
* @Action			注解 用于标注action配置
* @RequestMapping	URL注解 配置action请求路径
* @RequestParam	参数注解 配置请求参数
* @ResponseBody	配置异步响应数据
* 
* 其它实例注解
* @Autowired	注入注解 实例注入对像
* @BeanToTable	表实体注解 标注实体bean类影射到数据表 
* @Bean		对像注解 标注bean 属性字段为一个对像
* @Column		列注解 标注bean 属性字段为一个表字段
* @Sequences	序列注解 用于oracle序列名
* @Transaction	事务注解 用在service 层类上
* 
* 代码里包括源码和示例.
* 作者 YHY
