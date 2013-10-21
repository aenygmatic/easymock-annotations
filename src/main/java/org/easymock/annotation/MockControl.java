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
package org.easymock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.easymock.MockType;

/**
 * Fields annotated with {@code @MockControl} will be filled up with {@link org.easymock.IMocksControl IMocksControl}
 * object after the test class is initialized by {@link EasyMockAnnotations#initialize(Object)}.
 *
 * @author Balazs Berkes
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockControl {

    /**
     * Defines the type of mocks created by this control {@link MockType}.
     * Default is {@link MockType#DEFAULT}.
     * <p>
     * This setup of the {@code @Mock} annotation will be overridden by this setup if created by control.
     * <p>
     * @return the type of the mock
     */
    MockType value() default MockType.DEFAULT;
}
