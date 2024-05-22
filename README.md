<h2>美肴外卖平台</h2>
<h4>项目时间 2024-05-8 至 2024-05-22</h4>
<h3>一、项目介绍</h3>
美肴外卖项目是为餐饮企业（餐厅、饭店）定制的一款软件产品，包括"系统管理后台"和"小程序端应用"两部分。

其中系统管理后台主要提供给餐饮企业内部员工使用，可以对餐厅的分类、菜品、套餐、订单、员工等进行管理维护，
对餐厅的各类数据进行统计，同时也可进行来单语音播报功能。

小程序端主要提供给消费者使用，可以在线浏览菜品、添加购物车、下单、支付、催单等。

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/adb1f5ce-8497-413c-b32b-6e4f38f8aa43)

<h3>业务流程</h3>

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/206e91ca-1bdc-463a-a554-d2ab86bef8ad)

<h2>二、项目要点</h2>
<h4>1、 使用标准化开发流程</h4>

使用DTO类接受发送的请求参数，使用entity实体类对应数据库的数据表，使用VO类作为响应数据
的封装，将项目中使用的常量数据保存在各类型Constant类中, 封装Result类，将所有响应返回数据用Result类封装


<h4>2、使用Swagger – 框架</h4>

在接口文档的在线生成—doc.html ，导入Knife4j依赖 ，加入配置，设置资源映射 ，使用注解在对应位置
@Api(tags = “”)—用在类上
@ApiOperation（方法上）


<h4>3、JWT</h4>

将原始JSON进行安全封装，由Header（令牌类型、签名算法等）,载荷（携带信息），签名（防伪）组成  应用于身份验证、授权、信息交换


<h4>4、拦截器Interceptor – IOC容器</h4>

自定义类实现接口HandlerInterceptor，重写preHandle方法，交给IOC容器管理。自定义拦截器注册类


<h4>5、使用AOP 切面</h4>
横切关注点与业务逻辑分离，将其通用行为封装至横向模块，以达到对业务逻辑的增强。

过程： 1、起步依赖 2、编写自定义注解类，确定使用时间和空间 3、定义切面类（@Aspect），确定通知类型（@Before/@After/@Around），在方法中编写代码 4、根据注解类的限制，在需要使用的方法/类上增加注解进行使用



<h4>6、Redis非关系型数据库</h4>

基于内存的key-value结构数据库 , 应用场景：缓存，消息队列，排行榜，分布式锁等

用法：1、引入依赖 2、配置redis数据源 3、编写配置类，配置RedisTemplate对象 4、调用RedisTemplate对象，根据相关命令操作redis


<h4>7、HttpClient</h4>

发送Http请求，接收响应数据

 开发流程： 1、创建HttpClient对象 2、创建请求方法的实例，并指定请求URL 3、调用HttpClient对象的execute执行请求 4、释放连接
 

 <h4>8、Spring Cache</h4>
 基于注解的缓存功能，简化开发
-@EnableCaching 开启缓存 – 加在启动类上  – @Cacheable（cacheNames = “”, key = “spel表达式”）返回缓存数据，无数据则查数据库进行缓存并返回 

– @CachePut（value = “”,key = “spel表达式”） 放入缓存– @CacheEvict（cacheNames = " ",key = “spel”） 删除缓存


<h4>9、.Spring Task – 任务调度工具</h4>

用于取消超时系统未支付订单，处理每天为确认完成订单

开发流程：1.@ EnableScheduling 2.新建测试类交给IOC容器管理 3. @Scheduled(cron = “cron表达式”)


<h4>10、WebSocket</h4>

基于TCP连接的全双工通信网络协议， 使用WebSocket建立管理端与服务端的会话，当有新的订单产生时，推送信息给商家管理端


<h4>11、阿里云OSS</h4>
云存储服务,存储文本、图片、视频


<h4>12、事务处理translational</h4>
指一组原子性的操作序列，这些操作要么全部执行，要么全部不执行
在需要事务管理的方法上加注解：@Transactional（）


<h4>13、Apache POI</h4>

操作Excel文件

开发流程：1.导入依赖 ； 2.新建文件对象 new XSSFWorkbook();  3.创建Sheet页 excel.createSheet(“”) ；4.创建行sheet.createRow(0)—0为第一行；5.创建单元格 row.createCell(0) – row行第一格；6.赋值 setCellValue ； 7.写出磁盘，关流


<h3>三、项目效果</h3>
<h4>1、用户端流程</h4>

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/12b6e9d0-5fea-4153-baa3-684e9ba8129f)

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/6f0f66ef-080f-44a9-bcc7-9a3bc358b896)

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/2be04bdf-0b06-4d4f-9553-ccb1004ffe74)

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/50824910-97b9-4024-b805-dd2abc0cdbe1)

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/381107d2-f563-4720-bed9-d62603c2c3dc)

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/a0b889da-e4fd-440e-b8af-d00b8c4825b5)

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/69800a67-bb5e-4f5c-8403-1c7b9ed041e3)

<h4>2、管理端效果</h4>
来单提醒

![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/eba84d5f-9722-4baf-9dba-bc2c1f4b8fe4)

来单信息
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/300aaff9-0f8d-42dc-99b3-ec080084c33e)

接单菜品准备中
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/76042b6d-b7e3-433d-8fb5-c7b13da9fca5)

外卖派送
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/1a69284c-f251-4354-9d33-5fc497da5409)

订单完成
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/fe161b43-cf67-434e-a0d6-ba396278888e)

菜品管理
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/c1a09f0e-c84c-44e3-8f6f-19767527f11e)

新增菜品
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/474297aa-8289-42b7-b5d1-40b39b25a24a)

分类管理
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/11285fb7-3040-4bdd-8266-c626f0a8100b)

数据统计
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/e1900c80-38b9-44a3-98d5-886ff0d85bf4)

数据导出
![image](https://github.com/hbaiqiao/sky-take-out/assets/79921484/20f0dedb-3fde-4377-80c6-58edcdf3aeda)










