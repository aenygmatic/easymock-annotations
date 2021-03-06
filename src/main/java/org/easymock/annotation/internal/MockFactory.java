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

import org.easymock.MockType;

/**
 * Interface which provides an API for creating mocks.
 *
 * @author Balazs Berkes
 */
public interface MockFactory {

    /**
     * Created a mock object of the given class.
     * <p>
     * @param <T> type of class
     * @param clazz type of the mock
     * @param type {@link MockType} of the mock
     * @return returns a mocked object.
     */
    <T> T createMock(Class<T> clazz, MockType type);

    /**
     * Created a mock object of the given class.
     * <p>
     * @param <T> type of class
     * @param clazz type of the mock
     * @param type {@link MockType} of the mock
     * @param name name of the mock
     * @return returns a mocked object.
     */
    <T> T createMock(Class<T> clazz, MockType type, String name);
}
