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

package io.github.thierrysquirrel.rocketmq.core.consumer;

import io.github.thierrysquirrel.rocketmq.core.factory.execution.MethodFactoryExecution;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName: AbstractRocketListener
 * Description:
 * date: 2019/4/27 17:07
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public abstract class AbstractRocketListener {
    private static final Logger logger = Logger.getLogger(AbstractRocketListener.class.getName());


    private MethodFactoryExecution methodFactoryExecution;

    protected AbstractRocketListener(MethodFactoryExecution methodFactoryExecution) {
        this.methodFactoryExecution = methodFactoryExecution;
    }

    public void printErrorLog() {
        String logMsg = "Consumer agent failed！bean:" + methodFactoryExecution.getBean();
        logger.log(Level.WARNING, logMsg);
    }

    public MethodFactoryExecution getMethodFactoryExecution() {
        return methodFactoryExecution;
    }

    public void setMethodFactoryExecution(MethodFactoryExecution methodFactoryExecution) {
        this.methodFactoryExecution = methodFactoryExecution;
    }
}
