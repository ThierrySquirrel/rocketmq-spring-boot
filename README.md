# rocketmq-spring-boot-starter

AliYun RocketMQ Spring Book Edition

[中文](./README_zh_CN.md)

The project follows the [Apache2.0 License](http://www.apache.org/licenses/)  
The license is free. You can download the code, modify it as needed, and use it in your own projects  
When I comply with the Apache 2.0 License, I will not force others to comply with the Apache 2.0 License  
The donation address is below  

[Donate](https://www.paypal.com/paypalme/thierrysquirrel)

Support function:

- [x] Three modes of sending ordinary messages: synchronous, asynchronous and one-way
- [x] Subscribe to Message Cluster, Broadcast
- [x] Send and receive sequential messages
- [x] Transaction messages
- [x] Delay message
- [x] receive and receive timing messages

# Timing message and delay message:
Delay message and timing message:
In the official case, delayed news is much the same as regular news, essentially ordinary news.  

If delay message and timing message are needed, it is recommended to use timing task (timing task scheduling platform)  

To achieve the purpose of delay or timing.  


# Transaction message：
 In the framework, the operations on transaction messages are simpler and simpler. You can complete the transaction messages by annotations only.  
 
 Whether transactional messages, distributed transactional solutions or cross-platform language solutions, the core problem of transactional solutions is to ensure that messages can be sent and consumers can consume them.  
 
 Reliability Guarantee  
 
 1.Add @TransactionMessage annotation, kernel guarantee, local transaction error, do not send message, correct execution, send message, that is, default submission.  
 
 2.Reliability assurance is adopted by default, and default submission is checked back. The reason comes from the previous factor, which guarantees that local transactions do not go wrong.  
 
## Quick Start

```xml
<!--Adding dependencies to pom. XML-->
        <dependency>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <groupId>com.github.thierrysquirrel</groupId>
            <version>2.3.1.4-RELEASE</version>
        </dependency>
```
 ### configuration file
     
 
 ```properties
 ## application.properties
thierrysquirrel.access-key= #The Access Key you created in the AliYun Account Management Console for authentication
thierrysquirrel.secret-key= #The SecretKey you created in the AliYun Account Management Console for authentication
thierrysquirrel.name-srv-addr= #Set up TCP protocol access point and get it from console
 ```
# Start RocketMQ
```java
@SpringBootApplication
@EnableRocketMQ
public class DemoApplication{
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }
   
}
```
# Three Ways to Send Common Messages

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
# Send sequential messages
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
# Send Transaction Messages
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
# Delay message or timing message
```java
@RestController
@RocketMessage(groupID = "GID_delayed")
public class Delayed {
    //startDeliverTime is the time stamp, which cannot be less than the current time
    @GetMapping("/delayed")
    @CommonMessage(topic = "delayed", tag = "delayed")
    public String delayed(@StartDeliverTime @RequestParam("startDeliverTime") long startDeliverTime) {
        return "delayed";
    }
}
```
# Subscribe to regular, transactional, delayed, timed messages
Monitor messages using messageModel to control cluster or broadcast consumption patterns
```java
@RocketListener(groupID = "GID_message",messageModel = PropertyValueConst.CLUSTERING)
public class Delayed {
    @MessageListener(topic = "message",tag = "message")    
    public void delayed(String message) {
        System.out.println("message");
    }
}
```
# Subscription order message
```java
@RocketListener(groupID = "GID_message",messageModel = PropertyValueConst.BROADCASTING)
public class Delayed {
    @MessageListener(topic = "message",tag = "message", orderConsumer = true)
    public void delayed(String message) {
            System.out.println("message");
    }
}
```
# Batch Mode
```java
@RocketListener(groupID = "GID_message",batchConsumer = true)
public class Delayed {
	@MessageListener(topic = "message",tag = "message", orderConsumer = true)
	public void delayed(String message) {
		System.out.println("message");
	}
}
```

# Developer-defined global module
## Custom Implementation of Message Sending Results
```java
    @Component
    public class MySendCallback implements SendCallback {
        @Override
        public void onSuccess(SendResult sendResult) {
            System.out.println("Successful sending of message");
        }
        @Override
        public void onException(OnExceptionContext context) {
            System.out.println("Failed to send message");
        }
    }
```
## Customize whether local transactions are executed
```java
@Component
public class MyTransactionExecuter implements LocalTransactionExecuter {
    @Override
    public TransactionStatus execute(Message msg, Object arg) {
        System.out.println("Executing local affairs");
        return TransactionStatus.CommitTransaction;
    }
}
```

## Custom review of local transactions
```java
@Component
public class MyTransactionChecker implements LocalTransactionChecker {
    @Override
    public TransactionStatus check(Message msg) {
        System.out.println("Review of local transactions");
        return TransactionStatus.CommitTransaction;
    }
}
```
## Custom RocketSerializer
```java
@Component
public class JacksonSerializer implements RocketSerializer {
	private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(Object obj) {
        //omit
    }
    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        //omit
    }
}
```

# Developer-defined Local Modules
@CommonMessage callback Specify the class

@TransactionMessage checker And executer Specify the class


