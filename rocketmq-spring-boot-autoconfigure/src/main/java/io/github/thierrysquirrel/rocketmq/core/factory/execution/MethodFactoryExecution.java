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

package io.github.thierrysquirrel.rocketmq.core.factory.execution;

import io.github.thierrysquirrel.rocketmq.core.factory.MethodFactory;
import io.github.thierrysquirrel.rocketmq.core.serializer.RocketSerializer;
import io.github.thierrysquirrel.rocketmq.error.RocketException;

import java.lang.reflect.Method;

/**
 * ClassName: MethodFactoryExecution
 * Description:
 * date: 2019/4/27 16:26
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class MethodFactoryExecution {
    private Object bean;
    private Method method;
    private RocketSerializer rocketSerializer;

    public MethodFactoryExecution(Object bean, Method method, RocketSerializer rocketSerializer) {
        this.bean = bean;
        this.method = method;
        this.rocketSerializer = rocketSerializer;
    }

    public void methodExecution(byte[] message) throws RocketException {
        try {
            Class<?> methodParameter = MethodFactory.getMethodParameter(method);
            Object methodParameterBean = rocketSerializer.deSerialize(message, methodParameter);
            method.invoke(bean, methodParameterBean);
        } catch (Exception e) {
            throw new RocketException(e);
        }
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public RocketSerializer getRocketSerializer() {
        return rocketSerializer;
    }

    public void setRocketSerializer(RocketSerializer rocketSerializer) {
        this.rocketSerializer = rocketSerializer;
    }

    @Override
    public String toString() {
        return "MethodFactoryExecution{" +
                "bean=" + bean +
                ", method=" + method +
                ", rocketSerializer=" + rocketSerializer +
                '}';
    }
}
