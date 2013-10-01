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

import static org.mockannotations.utils.MockAnnotationReflectionUtils.getAllDeclaredFields;
import static org.mockannotations.utils.MockAnnotationReflectionUtils.getField;
import static org.mockannotations.utils.MockAnnotationReflectionUtils.setField;
import static org.mockannotations.utils.MockAnnotationValidationUtils.assertNotNull;
import static org.mockannotations.utils.MockAnnotationValidationUtils.isNull;
import static org.mockannotations.utils.MockAnnotationValidationUtils.notNull;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.mockannotations.ClassInitializer;
import org.mockannotations.MockHolder;
import org.mockannotations.MockInjector;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.easymock.MockType;
import org.easymock.TestSubject;

import org.easymock.annotation.internal.IMockControlFactory;

/**
 * Initialize the testclass. Scans for the {@link Mock @Mock}, {@link MockControl @MockControl} and
 * {@link Injected @Injected} annotations.
 * <p>
 * @author Balazs Berkes
 */
public class EasyMockAnnotations {

    /**
     * Initialize the testclass. Scans the testclass for {@code @Mock}, {@code @MockControl} and {@code @Injected}
     * annotations. If {@code @MockControl} is presented mocks will be created by the first annotated
     * {@link IMocksControl} or the {@code IMocksControl} with name associated with the
     * {@link Mock#control() @Mock.control()}. If no {@code @MockControl} annotation is presented mocks are created by
     * EasyMock (equals to {@code EasyMock.createMock(Class)}) or if the testclass is an instance of
     * {@link EasyMockSupport} (equals to {@code createMock(class)}).
     * Initialize all field annotated with {@link Mock @Mock}.
     * <p>
     * All the mocks are injected to field annotated with {@link Injected @Injected}. When the
     * {@code Injected @Injected} field is not initialized a new instance will be created if it has default constructor.
     * <p>
     * Usage:
     * <pre>
     *     &#064;Before
     *     public void setUp() {
     *         EasyMockAnnotations.initialize(this);
     *     }
     * </pre>
     *
     * @param testclass the testclass
     */
    public static void initialize(Object testclass) {
        assertNotNull(testclass, "Test class cannot be null!");
        new EasyMockAnnotationsInitializer().initialize(testclass);

    }

    private EasyMockAnnotations() {
    }

    private static class EasyMockAnnotationsInitializer {

        private final NavigableMap<String, IMocksControl> namedControls = new TreeMap<String, IMocksControl>();
        private final IMockControlFactory controlFactory = IMockControlFactory.getSingleton();
        private final ClassInitializer classInitializer = new ClassInitializer();
        private final List<MockHolder> mocks = new LinkedList<MockHolder>();
        private final MockInjector mockInjector = new MockInjector(mocks);

        private FallbackMockHolderFactory fallbackFactory;
        private Object testclass;

        private void initialize(Object testclass) {
            this.testclass = testclass;
            initializeMockControls();
            initializeMockFactories();
            initializeMocks();
            initializeTestedClasses();
        }

        private void initializeMockControls() {
            for (Field field : getAllDeclaredFields(testclass.getClass())) {
                if (field.isAnnotationPresent(MockControl.class)) {
                    createAndInjectControl(field);
                }
            }
        }

        private void createAndInjectControl(Field field) {
            assertFieldType(field);
            IMocksControl control = createControl(field);
            injectToTestclass(field, control);
            namedControls.put(field.getName(), control);
        }

        private void assertFieldType(Field field) throws RuntimeException {
            if (field.getType() != IMocksControl.class) {
                throw new RuntimeException("Field annotated with @MockControl must be type of org.easymock.IMocksControl!");
            }
        }

        private IMocksControl createControl(Field field) {
            MockControl annotation = field.getAnnotation(MockControl.class);
            IMocksControl control = controlFactory.createControl(annotation.value());
            return control;
        }

        private void initializeMockFactories() {
            fallbackFactory = new FallbackMockHolderFactory(namedControls, testclass);
        }

        private void initializeMocks() {
            for (Field field : getAllDeclaredFields(testclass.getClass())) {
                createAndInjectMock(field);
            }
        }

        private void initializeTestedClasses() {
            for (Field field : getAllDeclaredFields(testclass.getClass())) {
                if (field.isAnnotationPresent(Injected.class) || field.isAnnotationPresent(TestSubject.class)) {
                    Object testedClass = createInstanceIfNull(field);
                    mockInjector.injectTo(testedClass);
                }
            }
        }

        private Object createInstanceIfNull(Field field) {
            Object testedClass = getField(field, testclass);
            if (isNull(testedClass)) {
                testedClass = classInitializer.initialize(field.getType(), mocks);
                injectToTestclass(field, testedClass);
            }
            return testedClass;
        }

        private void createAndInjectMock(Field field) {
            Mock annotation = field.getAnnotation(Mock.class);
            if (notNull(annotation)) {
                processMockAnnotation(field, annotation.name(), annotation.value(), annotation.control());
            } else {
                processEasymockAnnotationIfPresented(field);
            }
        }

        private void processMockAnnotation(Field field, String name, MockType type, String control) {
            MockHolder mock = fallbackFactory.createMock(field, name, type, control);
            mocks.add(mock);
            injectToTestclass(field, mock.getMock());
        }

        private void processEasymockAnnotationIfPresented(Field field) {
            org.easymock.Mock easyMockAnnotation = field.getAnnotation(org.easymock.Mock.class);
            if (notNull(easyMockAnnotation)) {
                processMockAnnotation(field, easyMockAnnotation.name(), easyMockAnnotation.type(), "");
            }
        }

        private void injectToTestclass(Field field, Object control) {
            setField(field, testclass, control);
        }
    }
}
