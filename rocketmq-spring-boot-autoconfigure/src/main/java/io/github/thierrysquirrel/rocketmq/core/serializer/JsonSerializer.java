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

package io.github.thierrysquirrel.rocketmq.core.serializer;


import io.github.thierrysquirrel.json.util.JsonUtil;

/**
 * ClassName: JsonSerializer
 * Description:
 * date: 2019/1/28
 *
 * @author ThierrySquirrel
 * @since JDK 21
 */
public class JsonSerializer implements RocketSerializer {

    @Override
    public <T> byte[] serialize(T object) {
        String typeName = object.getClass().getTypeName();
        if (typeName.equals(String.class.getTypeName())) {
            return ((String) object).getBytes();
        }
        return JsonUtil.toJson(object).getBytes();
    }

    @Override
    public <T> T deSerialize(byte[] bytes, Class<T> clazz) {
        String typeName = clazz.getTypeName();
        if (typeName.equals(String.class.getTypeName())) {
            return (T) new String(bytes);
        }
        return JsonUtil.deSerialize(new String(bytes), clazz);
    }
}
