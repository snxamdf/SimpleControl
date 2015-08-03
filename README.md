# SimpleControl
* 简单的基于servlet,编写的java web 控制层类似spring mvc 事务配置，注解配置。 
* 
* 封装表单提交数据,可以对bean对像或list集合请求数据封装。
* 可对数据对你封装，直接进行实体插入更新，或查询删除等。
* 封装上传文件对像
* 自动装配service层，dao层对像。 
* 返回ajax数据和普通jsp页面 
* 在service层可以开启事务，添加事务注解
* *.properties配置文件注解取值
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
* @Bean		对像注解 标注bean属性字段为一个对像 已删除
* @Column		列注解 标注bean属性字段为一个表字段
* @Sequences	序列注解 用于oracle序列名
* @Transaction	事务注解 用在service 层类上
* @value 配置文件注解取值
* @Identify 唯一标识注解
* @Service service层注解
* @Dao dao层注解
* 
* 代码里包括源码和示例.
* 作者 YHY
*
* 框架里面用到了lombok.jar @Data注解自动生成bean实体get set方法，在eclipse里需要安装lombok插件，
* 直接打开lombok就可以安装.或在eclipse.ini配置文件里添加
* -javaagent:lombok.jar 
* -Xbootclasspath/a:lombok.jar
* 同时把lombok.jar放到eclipse.ini同目录下

