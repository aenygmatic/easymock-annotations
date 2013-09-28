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

/**
 * Fields annotated with {@code @Injected} will be injected with mocks created by
 * {@link org.easymock.annotation.Mock @Mock} annotation after the testclass is initialized by
 * {@link EasyMockAnnotations#initialize(Object)}.
 *
 * <p>
 * Injection priority
 * <ul>
 * <li>If two or more mock object can be injected to a field the closest by inheritance will be chosen</li>
 * <li>If two or more mock object has the same inheritance distance the mock will be injected by name (equals/equals
 * lowercase)</li>
 * <li>If the mock cannot be selected by these rules the first mock will be chosen</li>
 * </ul>
 * The same mock object can be used multiple times.
 *
 * @author Balazs Berkes
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injected {
}
