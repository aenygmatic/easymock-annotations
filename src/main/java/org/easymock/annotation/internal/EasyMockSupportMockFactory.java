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

import org.easymock.EasyMockSupport;
import org.easymock.MockType;

/**
 * Creates mock via the given class which inherits from {@link EasyMockSupport}.
 *
 * @author Balazs Berkes
 */
public class EasyMockSupportMockFactory implements MockFactory {

    private final EasyMockSupport easyMockSupport;

    public EasyMockSupportMockFactory(EasyMockSupport easyMockSupport) {
        this.easyMockSupport = easyMockSupport;
    }

    @Override
    public <T> T createMock(Class<T> clazz, MockType type) {
        T mock;
        switch (type) {
            case NICE:
                mock = easyMockSupport.createNiceMock(clazz);
                break;
            case STRICT:
                mock = easyMockSupport.createStrictMock(clazz);
                break;
            case DEFAULT:
            default:
                mock = easyMockSupport.createMock(clazz);
                break;
        }
        return mock;
    }

    @Override
    public <T> T createMock(Class<T> clazz, MockType type, String name) {
        T mock;
        switch (type) {
            case NICE:
                mock = easyMockSupport.createNiceMock(name, clazz);
                break;
            case STRICT:
                mock = easyMockSupport.createStrictMock(name, clazz);
                break;
            case DEFAULT:
            default:
                mock = easyMockSupport.createMock(name, clazz);
                break;
        }
        return mock;
    }
}
