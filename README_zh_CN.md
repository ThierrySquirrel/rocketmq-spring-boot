# rocketmq-spring-boot-starter

阿里云RocketMQ   SpringBoot 版

[English](./README.md)

[感谢JetBrains软件的帮助](https://jb.gg/OpenSource)

该项目遵循[Apache2.0 License](http://www.apache.org/licenses/)  
许可证是免费的.您可以下载代码,根据需要进行修改,并在自己的项目中使用它  

重要提示:作者在GitHub上发布的所有项目都是非营利的.  
自从作者注册GitHub以来,到目前为止,他一分钱也没赚到.  
任何对作者的项目收费的人都在欺骗你.  
如果你被骗了,我希望你能得到律师的帮助.祝你好运.

重要提示：作者目前生活在没有收入的悲惨境地,钱也快用完了.  
项目依然遵循[Apache2.0 License](http://www.apache.org/licenses/)  
如果作者没钱吃饭,作者无法在没有网络和生活的地方免费为大家更新项目.  
作者现接受捐款,感谢大家的支持.  
开始接受捐赠的时间是此文本当前更新的时间.  
[捐款](https://www.paypal.com/paypalme/thierrysquirrel)  

重要提示：此项目现已停止维护,因为作者在维护项目的同时需要支付维护商业版本的费用.  
我们今天花了钱维护商业版本，但无法使用该产品.  
我们决定暂停所有阿里产品的维护或更新.  
开源项目的维护者不会支付企业家免费工作的费用，因为他们会禁用您的帐户.  

重要提示：阿里决定制裁我，今天我收到一条短信,他强迫我支付维护阿里产品的费用.  
我昨天支付了我需要支付的费用,也就是维护阿里产品,然后取消了付费产品,但无论如何我都被制裁了,现在我不得不支付更多费用.  
祝大家好运,不要被阿里制裁.  

支持功能：
- [x] 普通消息三种发送方式：同步，异步，单向
- [x] 订阅消息集群,广播
- [x] 收发顺序消息
- [x] 收发事务消息
- [x] 收发延迟消息
- [x] 收发定时消息

# 定时消息与延时消息：
 收发延时消息与定时消息：  
 
 在官方例子中，延时消息与定时消息大同小异，本质上都为普通消息  
 
 如果需要延时消息，与定时消息，建议使用定时任务（定时任务调度平台）  
 
 达到延时或定时的目的。

# 事务消息：
 在框架中,在事务消息上的操作更为化繁为简,您只需要通过注解,即可完成事务消息  
 
 无论事务消息,分布式事务方案,跨平台语言解决方案,其核心解决事务关键问题,在于确保消息能够发送,确保消费者能够消费  
 
 可靠性保障  
 
 1.代码块中加入@TransactionMessage注解,内核保障,本地事务出错,不发送消息,正确执行,则发送消息,即为默认提交  
 
 2.可靠性保障默认采取,回查默认提交,其原因来自上一条因素,保障本地事务不出错  
 
## Quick Start

```xml
<!--在pom.xml中添加依赖-->
        <dependency>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <groupId>com.github.thierrysquirrel</groupId>
            <version>2.3.1.4-RELEASE</version>
        </dependency>
```
 ### 配置文件
 
 ```properties
 ## application.properties
thierrysquirrel.access-key= #您在阿里云账号管理控制台中创建的 AccessKey，用于身份认证
thierrysquirrel.secret-key= #您在阿里云账号管理控制台中创建的 SecretKey，用于身份认证
thierrysquirrel.name-srv-addr= #设置 TCP 协议接入点，从控制台获取
 ```
# 启动RocketMQ
```java
@SpringBootApplication
@EnableRocketMQ
public class DemoApplication{
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }
   
}
```
# 发送普通消息三种方式

```java
@RestController
@RocketMessage(groupID = "GID_common")
public class Common {

    @GetMapping("/commonA")
    @CommonMessage(topic = "commonA", tag = "commonA",messageSendType = MessageSendType.SEND)
    public String sendCommonMsg() {
        return "commonA";
    }
    @GetMapping("/commonB")
    @CommonMessage(topic = "commonB", tag = "commonB",messageSendType = MessageSendType.SEND_ASYNC)
    public String sendAsyncMsg() {
        return "commonB";
    }
    @GetMapping("/commonC")
    @CommonMessage(topic = "commonC", tag = "commonC",messageSendType = MessageSendType.SEND_ONE_WAY)
    public String sendOneWayMessage() {
        return "commonC";
    }
}
```
# 发送顺序消息
```java
@RestController
@RocketMessage(groupID = "GID_order")
public class Order {
    @GetMapping("/order")
    @OrderMessage(topic = "order",tag = "order")
    public String order(@RequestParam @ShardingKey String shardingKey) {
        return "order";
    }
}
```
# 发送事务消息
```java
@RestController
@RocketMessage(groupID = "GID_transaction")
public class Transaction {
    @GetMapping("/transaction")
    @TransactionMessage(topic = "transaction",tag = "transaction")
    public String transaction() {
        return "transaction";
    }
}
```
# 发送延时消息或定时消息
```java
@RestController
@RocketMessage(groupID = "GID_delayed")
public class Delayed {
    //startDeliverTime是时间戳,不能小于当前时间
    @GetMapping("/delayed")
    @CommonMessage(topic = "delayed", tag = "delayed")
    public String delayed(@StartDeliverTime @RequestParam("startDeliverTime") long startDeliverTime) {
        return "delayed";
    }
}
```
# 订阅普通、事务、延时、定时消息
## 监听消息使用 messageModel 控制集群或广播消费模式

```java
@RocketListener(groupID = "GID_message",messageModel = PropertyValueConst.CLUSTERING)
public class Delayed {
    @MessageListener(topic = "message",tag = "message")    
    public void delayed(String message) {
            System.out.println("message");
    }
}
```
# 订阅顺序消息
```java
@RocketListener(groupID = "GID_message",messageModel = PropertyValueConst.BROADCASTING)
public class Delayed {
    @MessageListener(topic = "message",tag = "message", orderConsumer = true)
    public void delayed(String message) {
            System.out.println("message");
    }
}

```
# 批量模式
```java
@RocketListener(groupID = "GID_message",batchConsumer = true)
public class Delayed {
    @MessageListener(topic = "message",tag = "message", orderConsumer = true)
    public void delayed(String message) {
            System.out.println("message");
    }
}
```

# 开发者自定义全局模块
## 自定义实现消息发送结果
```java
    @Component
    public class MySendCallback implements SendCallback {
        @Override
        public void onSuccess(SendResult sendResult) {
            System.out.println("发送消息成功");
        }
        @Override
        public void onException(OnExceptionContext context) {
            System.out.println("发送消息失败");
        }
    }
```
## 自定义本地事务是否执行
```java
@Component
public class MyTransactionExecuter implements LocalTransactionExecuter {
    @Override
    public TransactionStatus execute(Message msg, Object arg) {
        System.out.println("执行本地事务");
        return TransactionStatus.CommitTransaction;
    }
}
```

## 自定义回查本地事务
```java
@Component
public class MyTransactionChecker implements LocalTransactionChecker {
    @Override
    public TransactionStatus check(Message msg) {
        System.out.println("回查本地事务");
        return TransactionStatus.CommitTransaction;
    }
}
```

## 自定义 Mq序列化器
```java
@Component
public class JacksonSerializer implements RocketSerializer {
	private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> byte[] serialize(T object) {
        //omit
    }
    @Override
    public <T> T deSerialize(byte[] bytes, Class<T> clazz) {
        //omit
    }
}
```
# 开发者自定义局部模块
@CommonMessage callback指定class

@TransactionMessage checker与executer 指定class

![AmericanFlag](https://user-images.githubusercontent.com/49895274/184470801-86f615b1-1b69-4d0c-ad68-41c84549ec40.jpeg)  

