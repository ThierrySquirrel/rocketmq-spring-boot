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

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * ClassName: RocketProperties
 * Description:
 * date: 2019/4/25 15:56
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
@ConfigurationProperties(prefix = RocketProperties.ROCKET_PREFIX)
public class RocketProperties {

    public static final String ROCKET_PREFIX = "ons";
    /**
     * 设置 TCP 协议接入点，从控制台获取
     */
    private String nameSrvAddr;

    /**
     * 实际使用的实例ID
     */
    private String namespace;
    /**
     * RocketMq-5X系列,阿里云RocketMq控制台,控制访问,智能身份识别,用户名
     */
    private String accessKey;
    /**
     * RocketMq-5X系列,阿里云RocketMq控制台,控制访问,智能身份识别,密码
     */
    private String secretKey;
    /**
     * 用户渠道，默认为：ALIYUN，聚石塔用户为：CLOUD
     */
    private String onsChannel = "ALIYUN";
    /**
     * 设置消息发送的超时时间，单位（毫秒），默认：3000
     */
    private Integer sendMsgTimeoutMillis = 3000;
    /**
     * 设置事务消息第一次回查的最快时间，单位（秒）
     */
    private Integer checkImmunityTimeInSeconds = 5;
    /**
     * 设置 RocketMessage 实例的消费线程数，阿里云默认：20
     * 默认cpu数量*2+1
     */
    private Integer consumeThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 设置消息消费失败的最大重试次数，默认：16
     */
    private Integer maxReconsumeTimes = 16;
    /**
     * 设置每条消息消费的最大超时时间，超过设置时间则被视为消费失败，等下次重新投递再次消费。 每个业务需要设置一个合理的值，单位（分钟）。 默认：15
     */
    private Integer consumeTimeout = 15;
    /**
     * 只适用于顺序消息，设置消息消费失败的重试间隔时间默认100毫秒
     */
    private Integer suspendTimeMilli = 100;
    /**
     * 异步发送消息执行Callback的目标线程池
     */
    private Integer callbackThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 生产者发送消息线程数 默认cpu数量*2+1
     */
    private Integer sendMessageThreadNums = Runtime.getRuntime().availableProcessors() * 2 + 1;

    public String getNameSrvAddr() {
        return nameSrvAddr;
    }

    public void setNameSrvAddr(String nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOnsChannel() {
        return onsChannel;
    }

    public void setOnsChannel(String onsChannel) {
        this.onsChannel = onsChannel;
    }

    public Integer getSendMsgTimeoutMillis() {
        return sendMsgTimeoutMillis;
    }

    public void setSendMsgTimeoutMillis(Integer sendMsgTimeoutMillis) {
        this.sendMsgTimeoutMillis = sendMsgTimeoutMillis;
    }

    public Integer getCheckImmunityTimeInSeconds() {
        return checkImmunityTimeInSeconds;
    }

    public void setCheckImmunityTimeInSeconds(Integer checkImmunityTimeInSeconds) {
        this.checkImmunityTimeInSeconds = checkImmunityTimeInSeconds;
    }

    public Integer getConsumeThreadNums() {
        return consumeThreadNums;
    }

    public void setConsumeThreadNums(Integer consumeThreadNums) {
        this.consumeThreadNums = consumeThreadNums;
    }

    public Integer getMaxReconsumeTimes() {
        return maxReconsumeTimes;
    }

    public void setMaxReconsumeTimes(Integer maxReconsumeTimes) {
        this.maxReconsumeTimes = maxReconsumeTimes;
    }

    public Integer getConsumeTimeout() {
        return consumeTimeout;
    }

    public void setConsumeTimeout(Integer consumeTimeout) {
        this.consumeTimeout = consumeTimeout;
    }

    public Integer getSuspendTimeMilli() {
        return suspendTimeMilli;
    }

    public void setSuspendTimeMilli(Integer suspendTimeMilli) {
        this.suspendTimeMilli = suspendTimeMilli;
    }

    public Integer getCallbackThreadNums() {
        return callbackThreadNums;
    }

    public void setCallbackThreadNums(Integer callbackThreadNums) {
        this.callbackThreadNums = callbackThreadNums;
    }

    public Integer getSendMessageThreadNums() {
        return sendMessageThreadNums;
    }

    public void setSendMessageThreadNums(Integer sendMessageThreadNums) {
        this.sendMessageThreadNums = sendMessageThreadNums;
    }

    @Override
    public String toString() {
        return "RocketProperties{" +
                "nameSrvAddr='" + nameSrvAddr + '\'' +
                ", namespace='" + namespace + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", onsChannel='" + onsChannel + '\'' +
                ", sendMsgTimeoutMillis=" + sendMsgTimeoutMillis +
                ", checkImmunityTimeInSeconds=" + checkImmunityTimeInSeconds +
                ", consumeThreadNums=" + consumeThreadNums +
                ", maxReconsumeTimes=" + maxReconsumeTimes +
                ", consumeTimeout=" + consumeTimeout +
                ", suspendTimeMilli=" + suspendTimeMilli +
                ", callbackThreadNums=" + callbackThreadNums +
                ", sendMessageThreadNums=" + sendMessageThreadNums +
                '}';
    }
}
