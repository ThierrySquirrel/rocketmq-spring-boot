/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.thierrysquirrel.rocketmq.core.strategy;

import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import io.github.thierrysquirrel.rocketmq.annotation.CommonMessage;
import io.github.thierrysquirrel.rocketmq.annotation.OrderMessage;
import io.github.thierrysquirrel.rocketmq.annotation.RocketMessage;
import io.github.thierrysquirrel.rocketmq.annotation.TransactionMessage;
import io.github.thierrysquirrel.rocketmq.core.factory.ProducerConsumerFactory;
import io.github.thierrysquirrel.rocketmq.core.factory.SendMessageFactory;
import io.github.thierrysquirrel.rocketmq.error.RocketException;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * ClassName: ProducerStrategy
 * Description:
 * date: 2019/4/29 21:34
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class ProducerStrategy {
    private ProducerStrategy() {
    }

    public static void statsSendMessage(Long startDeliverTime, String shardingKeyFactory, Map<String, Object> consumerContainer, RocketMessage rocketMessage, Object message, byte[] bytes, ApplicationContext applicationContex) throws RocketException {
        if (message instanceof CommonMessage) {
            CommonMessage commonMessage = (CommonMessage) message;
            Producer producer = ProducerConsumerFactory.getProducer (consumerContainer, rocketMessage, commonMessage);
            SendMessageFactory.sendMessage (startDeliverTime, producer, commonMessage, bytes, applicationContex);
            return;
        }
        if (message instanceof OrderMessage) {
            OrderMessage orderMessage = (OrderMessage) message;
            OrderProducer orderProducer = ProducerConsumerFactory.getProducer (consumerContainer, rocketMessage, orderMessage);
            SendMessageFactory.sendMessage (orderProducer, orderMessage, bytes, shardingKeyFactory);
            return;
        }
        if (message instanceof TransactionMessage) {
            TransactionMessage transactionMessage = (TransactionMessage) message;
            TransactionProducer transactionProducer = ProducerConsumerFactory.getProducer (consumerContainer, rocketMessage, transactionMessage);
            SendMessageFactory.sendMessage (transactionProducer, transactionMessage, bytes, applicationContex);
        }
    }
}
