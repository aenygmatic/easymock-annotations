package org.easymock.annotation;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getField;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.setField;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;

import org.easymock.annotation.exception.EasyMockAnnotationInitializationException;
import org.easymock.annotation.exception.EasyMockAnnotationReflectionException;
import org.easymock.annotation.internal.ClassInitializer;
import org.easymock.annotation.internal.ControlledMockFactory;
import org.easymock.annotation.internal.EasyMockSupportMockFactory;
import org.easymock.annotation.internal.MockFactory;
import org.easymock.annotation.internal.MockHolder;
import org.easymock.annotation.internal.MockInjector;
import org.easymock.annotation.internal.StaricMockFactory;

/**
 * Initialize the testclass. Scans for the {@link Mock @Mock}, {@link MockControl @MockControl} and
 * {@link Injected @Injected} annotations.
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotations {

    private static MockInjector MOCK_INJECTOR = new MockInjector();

    /**
     * Initialize the test class.
     *
     * @param testclass
     * @return
     */
    public static IMocksControl initializeWithMockControl(Object testclass) {
        IMocksControl control = EasyMock.createControl();
        injectMockControl(testclass, control);
        Set<MockHolder> mocks = processEasyMockAnnotations(testclass, new ControlledMockFactory(control));
        Object testedObject = initializeTestedClass(testclass);
        injectToTestedClass(testedObject, mocks);
        return control;
    }

    /**
     *
     * @param testclass
     */
    public static void initialize(Object testclass) {
        MockFactory mockFactory;
        if (testclass instanceof EasyMockSupport) {
            mockFactory = new EasyMockSupportMockFactory((EasyMockSupport) testclass);
        } else {
            mockFactory = new StaricMockFactory();
        }
        Set<MockHolder> mocks = processEasyMockAnnotations(testclass, mockFactory);
        Object testedObject = initializeTestedClass(testclass);
        injectToTestedClass(testedObject, mocks);
    }

    private static Class<? extends Object> getClassOfTest(Object testclass) throws RuntimeException {
        if (testclass == null) {
            throw new RuntimeException("Test class cannot be null!");
        }
        return testclass.getClass();
    }

    private static void injectMockControl(Object testclass, IMocksControl control) {
        for (Field field : testclass.getClass().getDeclaredFields()) {
            MockControl annotation = field.getAnnotation(MockControl.class);
            if (annotation != null) {
                try {
                    setField(field, testclass, control);
                } catch (EasyMockAnnotationReflectionException ex) {
                    throw new EasyMockAnnotationInitializationException("Field annotated with @MockControl must be type implements org.easymock.IMocksControl!", ex);
                }
            }
        }
    }

    private static Object initializeTestedClass(Object testclass) {
        Object testedClass = null;
        for (Field field : testclass.getClass().getDeclaredFields()) {
            Injected annotation = field.getAnnotation(Injected.class);
            if (annotation != null) {
                testedClass = createInstanceIfNull(field, testclass);
            }
        }
        if (testedClass == null) {
            throw new EasyMockAnnotationInitializationException("Failed to initilize class under test");
        }
        return testedClass;
    }

    private static Set<MockHolder> processEasyMockAnnotations(Object testclass, MockFactory mockFactory) throws EasyMockAnnotationInitializationException {
        Class<? extends Object> clazz = getClassOfTest(testclass);
        Set<MockHolder> mocks = new HashSet<MockHolder>();

        for (Field field : clazz.getDeclaredFields()) {
            Mock annotation = field.getAnnotation(Mock.class);
            if (annotation != null) {
                Class<?> typeOfField = field.getType();
                Object mockedObject = mockFactory.createMock(typeOfField);
                MockHolder mock = createMock(mockedObject, field);
                mocks.add(mock);
                setField(field, testclass, mockedObject);
            }
        }
        return mocks;
    }

    private static void injectToTestedClass(Object testedObject, Set< MockHolder> mocks) {
        MOCK_INJECTOR.injectMocks(mocks).injectTo(testedObject);
    }

    private static MockHolder createMock(Object mockedObject, Field field) {
        MockHolder mock = new MockHolder();
        mock.setMock(mockedObject);
        mock.setSourceField(field);
        return mock;
    }

    private static Object createInstanceIfNull(Field field, Object testclass) {
        Object testedClass = getField(field, testclass);
        if (testedClass == null) {
            testedClass = new ClassInitializer().initialize(field.getType());
            setField(field, testclass, testedClass);
        }
        return testedClass;
    }
}
