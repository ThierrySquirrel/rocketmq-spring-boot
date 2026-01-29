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

package io.github.thierrysquirrel.rocketmq.core.factory;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import io.github.thierrysquirrel.rocketmq.annotation.RocketMessage;
import io.github.thierrysquirrel.rocketmq.autoconfigure.RocketProperties;

import java.util.Properties;

/**
 * ClassName: ProducerFactory
 * Description:
 * date: 2019/4/28 21:35
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class ProducerFactory {
    private ProducerFactory() {
    }

    public static Producer createProducer(RocketMessage rocketMessage, RocketProperties rocketProperties) {
        Properties properties = ProducerPropertiesFactory.createProducerProperties(rocketMessage, rocketProperties);
        return ONSFactory.createProducer(properties);
    }

    public static OrderProducer createOrderProducer(RocketMessage rocketMessage, RocketProperties rocketProperties) {
        Properties properties = ProducerPropertiesFactory.createProducerProperties(rocketMessage, rocketProperties);
        return ONSFactory.createOrderProducer(properties);
    }

    public static TransactionProducer createTransactionProducer(RocketMessage rocketMessage, RocketProperties rocketProperties, LocalTransactionChecker localTransactionChecker) {
        Properties properties = ProducerPropertiesFactory.createProducerProperties(rocketMessage, rocketProperties);
        properties.put(PropertyKeyConst.CheckImmunityTimeInSeconds, rocketProperties.getCheckImmunityTimeInSeconds());
        return ONSFactory.createTransactionProducer(properties, localTransactionChecker);
    }
}
