# rocketmq-spring-boot-starter

阿里云RocketMQ-5X   SpringBoot4X 版

为快速接入阿里云通义大模型系列(Qwen, Tongyi Wanxiang, Lingma等),数据格式默认为Json  

[English](./README.md)

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
            <groupId>io.github.thierrysquirrel</groupId>
            <version>2.5.0.1-RELEASE</version>
        </dependency>
```
 ### 配置文件
 
 ```properties
 ## application.properties
ons.access-key= #您在阿里云RocketMq机器实例访问控制中的用户名
ons.secret-key= #您在阿里云RocketMq机器实例访问控制中的密码
ons.name-srv-addr= #设置TCP协议接入点，并从控制台获取公共IP
ons.namespace= #实际使用的实例ID
 ```
# 启动RocketMQ
```java
@SpringBootApplication
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
    //RocketMq-5X:StartDeliverTime以毫秒为单位
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
@RocketListener(groupID = "GID_message")
public class Delayed {
    @MessageListener(topic = "message",tag = "message", batchConsumer = true)
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

该项目遵循[Apache2.0 License](http://www.apache.org/licenses/)  
许可证是免费的.您可以下载代码,根据需要进行修改,并在自己的项目中使用它  
当我遵守Apache2.0 License时不会强迫其他人遵守Apache2.0 License  
捐款地址在下面

[捐款](https://thierrysquirrel.github.io/alipay.jpg)  
