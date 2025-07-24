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

package io.github.thierrysquirrel.rocketmq.container;

import io.github.thierrysquirrel.rocketmq.annotation.RocketMessage;
import io.github.thierrysquirrel.rocketmq.autoconfigure.RocketProperties;
import io.github.thierrysquirrel.rocketmq.core.factory.ThreadPoolFactory;
import io.github.thierrysquirrel.rocketmq.core.strategy.RocketConsumerStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName: RocketProducerContainer
 * Description:
 * date: 2019/5/3 11:29
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class RocketProducerContainer implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	private final RocketProperties rocketProperties;
	private final Map<String, Object> consumerContainer;

	public RocketProducerContainer(Map<String, Object> consumerContainer, RocketProperties rocketProperties) {
		this.consumerContainer = consumerContainer;
		this.rocketProperties = rocketProperties;
	}

	@PostConstruct
	public void initialize() {
		ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.createProducerThreadPoolExecutor(rocketProperties);
		applicationContext.getBeansWithAnnotation(RocketMessage.class).forEach((beanName, bean) -> RocketConsumerStrategy.putProducer(threadPoolExecutor, consumerContainer, bean, rocketProperties, applicationContext));
		threadPoolExecutor.shutdown();
	}


	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
