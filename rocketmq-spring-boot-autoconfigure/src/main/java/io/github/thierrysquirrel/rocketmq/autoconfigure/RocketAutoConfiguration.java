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

package io.github.thierrysquirrel.rocketmq.autoconfigure;

import io.github.thierrysquirrel.rocketmq.aspect.RocketAspect;
import io.github.thierrysquirrel.rocketmq.container.RocketConsumerContainer;
import io.github.thierrysquirrel.rocketmq.container.RocketProducerContainer;
import io.github.thierrysquirrel.rocketmq.core.serializer.JsonSerializer;
import io.github.thierrysquirrel.rocketmq.core.serializer.RocketSerializer;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * ClassName: RocketProperties
 * Description:
 * date: 2019/4/25 15:57
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
@Configuration
@EnableConfigurationProperties(RocketProperties.class)
public class RocketAutoConfiguration {

    @Resource
    private Map<String, Object> consumerContainer;
    @Resource
    private RocketProperties rocketProperties;

    @Bean
    @ConditionalOnMissingBean(RocketConsumerContainer.class)
    public RocketConsumerContainer rocketConsumerContainer(@Autowired RocketSerializer rocketSerializer) {
        return new RocketConsumerContainer(rocketProperties, rocketSerializer);
    }

    @Bean
    @ConditionalOnMissingBean(RocketSerializer.class)
    public RocketSerializer rocketSerializer() {
        return new JsonSerializer();
    }

    @Bean
    @ConditionalOnMissingBean(Map.class)
    public Map<String, Object> consumerContainer() {
        return Maps.newConcurrentMap();
    }

    @Bean
    @ConditionalOnMissingBean(RocketProducerContainer.class)
    public RocketProducerContainer rocketProducerContainer() {
        return new RocketProducerContainer(consumerContainer, rocketProperties);
    }

    @Bean
    @ConditionalOnMissingBean(RocketAspect.class)
    public RocketAspect rockerAspect() {
        return new RocketAspect(consumerContainer, rocketProperties);
    }
}
