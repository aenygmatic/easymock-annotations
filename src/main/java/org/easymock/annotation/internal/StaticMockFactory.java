/* 
 * Copyright 2013 Balazs Berkes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.easymock.annotation.internal;

import org.easymock.EasyMock;
import org.easymock.MockType;

/**
 * Creates mock via EasyMock. This is equivalent to {@code  EasyMock.createMock(class)}
 *
 * @author Balazs Berkes
 */
public class StaticMockFactory implements MockFactory {

    @Override
    public <T> T createMock(Class<T> clazz, MockType type) {
        T mock;
        switch (type) {
            case NICE:
                mock = EasyMock.createNiceMock(clazz);
                break;
            case STRICT:
                mock = EasyMock.createStrictMock(clazz);
                break;
            case DEFAULT:
            default:
                mock = EasyMock.createMock(clazz);
                break;
        }
        return mock;
    }

    @Override
    public <T> T createMock(Class<T> clazz, MockType type, String name) {
        T mock;
        switch (type) {
            case NICE:
                mock = EasyMock.createNiceMock(name, clazz);
                break;
            case STRICT:
                mock = EasyMock.createStrictMock(name, clazz);
                break;
            case DEFAULT:
            default:
                mock = EasyMock.createMock(name, clazz);
                break;
        }
        return mock;
    }
}
