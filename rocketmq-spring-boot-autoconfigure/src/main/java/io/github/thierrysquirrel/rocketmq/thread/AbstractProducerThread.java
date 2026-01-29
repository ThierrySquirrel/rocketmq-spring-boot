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

package io.github.thierrysquirrel.rocketmq.thread;

import io.github.thierrysquirrel.rocketmq.annotation.RocketMessage;
import io.github.thierrysquirrel.rocketmq.autoconfigure.RocketProperties;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * ClassName: AbstractProducerThread
 * Description:
 * date: 2019/5/3 14:02
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public abstract class AbstractProducerThread implements Runnable {
    private Map<String, Object> producerConsumer;
    private RocketMessage rocketMessage;
    private Object bean;
    private RocketProperties rocketProperties;
    private ApplicationContext applicationContext;

    public AbstractProducerThread(Map<String, Object> producerConsumer, RocketMessage rocketMessage, Object bean, RocketProperties rocketProperties, ApplicationContext applicationContext) {
        this.producerConsumer = producerConsumer;
        this.rocketMessage = rocketMessage;
        this.bean = bean;
        this.rocketProperties = rocketProperties;
        this.applicationContext = applicationContext;
    }

    /**
     * 开始向容器装填
     *
     * @param producerConsumer   producerConsumer
     * @param rocketMessage      rocketMessage
     * @param bean               bean
     * @param rocketProperties   rocketProperties
     * @param applicationContext applicationContext
     */
    protected abstract void statsPutProducer(Map<String, Object> producerConsumer, RocketMessage rocketMessage, Object bean, RocketProperties rocketProperties, ApplicationContext applicationContext);

    @Override
    public void run() {
        statsPutProducer(producerConsumer, rocketMessage, bean, rocketProperties, applicationContext);
    }

    public Map<String, Object> getProducerConsumer() {
        return producerConsumer;
    }

    public void setProducerConsumer(Map<String, Object> producerConsumer) {
        this.producerConsumer = producerConsumer;
    }

    public RocketMessage getRocketMessage() {
        return rocketMessage;
    }

    public void setRocketMessage(RocketMessage rocketMessage) {
        this.rocketMessage = rocketMessage;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public RocketProperties getRocketProperties() {
        return rocketProperties;
    }

    public void setRocketProperties(RocketProperties rocketProperties) {
        this.rocketProperties = rocketProperties;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public String toString() {
        return "AbstractProducerThread{" +
                "producerConsumer=" + producerConsumer +
                ", rocketMessage=" + rocketMessage +
                ", bean=" + bean +
                ", rocketProperties=" + rocketProperties +
                ", applicationContext=" + applicationContext +
                '}';
    }
}
