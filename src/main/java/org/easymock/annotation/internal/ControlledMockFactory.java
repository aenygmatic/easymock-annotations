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

import org.easymock.IMocksControl;
import org.easymock.MockType;

/**
 * Creates mock via the given {@link IMocksControl}.
 *
 * @author Balazs Berkes
 */
public class ControlledMockFactory implements MockFactory {

    private final IMocksControl control;

    public ControlledMockFactory(IMocksControl control) {
        this.control = control;
    }

    /**
     * Creates a mock object of the given class.
     * <p>
     * @param clazz type of the mock
     * @param type {@link MockType} will be ignored
     * @return returns a mocked object.
     */
    @Override
    public <T> T createMock(Class<T> clazz, MockType type) {
        return control.createMock(clazz);
    }

    /**
     * Created a mock object of the given class.
     * <p>
     * @param clazz type of the mock
     * @param type {@link MockType} will be ignored
     * @param name name of the mock
     * @return returns a mocked object.
     */
    @Override
    public <T> T createMock(Class<T> clazz, MockType type, String name) {
        return control.createMock(name, clazz);
    }
}
