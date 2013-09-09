package org.easymock.annotation;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getAllFields;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getField;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.setField;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.easymock.MockType;
import org.easymock.TestSubject;

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

    /**
     * Initialize the test class. The mocks are created by {@link IMocksControl}.
     * Initialize all field annotated with {@link Mock @Mock}. The mock are under the conrol of the returned
     * {@code IMocksControl}.
     * <p>
     * When a field is annotated with {@link MockControl @MockControl} the control will be injected to the field.
     * All the mocks are injected to field annotated with {@link Injected @Injected}. When the
     * {@code Injected @Injected} field is not initalized a new instance will be created if it has default constructor.
     * <p>
     * Usage:
     * <pre>
     *     &#064;Before
     *     public void setUp() {
     *         IMocksControl control = EasyMockAnnotations.initializeWithMockControl(this);
     *     }
     * </pre>
     *
     * @param testclass the testclass
     * @return the {@code IMocksControl}
     */
    public static IMocksControl initializeWithMockControl(Object testclass) {
        IMocksControl control = injectMockControl(testclass);
        List<MockHolder> mocks = processEasyMockAnnotations(testclass, new ControlledMockFactory(control));
        Object testedObject = initializeTestedClass(testclass, mocks);
        if (testedObject != null) {
            injectToTestedClass(testedObject, mocks);
        }
        return control;
    }

    /**
     * Initialize the test class. The mocks are created by EasyMock (equals to {@code EasyMock.createMock(Class)}) or if
     * the testclass is an instance of {@link EasyMockSupport} (equals to {@code createMock(class)}).
     * Initialize all field annotated with {@link Mock @Mock}.
     * <p>
     * All the mocks are injected to field annotated with {@link Injected @Injected}. When the
     * {@code Injected @Injected} field is not initalized a new instance will be created if it has default constructor.
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
        MockFactory mockFactory;
        if (testclass instanceof EasyMockSupport) {
            mockFactory = new EasyMockSupportMockFactory((EasyMockSupport) testclass);
        } else {
            mockFactory = new StaricMockFactory();
        }
        List<MockHolder> mocks = processEasyMockAnnotations(testclass, mockFactory);
        Object testedObject = initializeTestedClass(testclass, mocks);
        if (testedObject != null) {
            injectToTestedClass(testedObject, mocks);
        }
    }

    private static Class<? extends Object> getClassOfTest(Object testclass) throws RuntimeException {
        if (testclass == null) {
            throw new RuntimeException("Test class cannot be null!");
        }
        return testclass.getClass();
    }

    private static IMocksControl injectMockControl(Object testclass) {
        IMocksControl control = null;
        for (Field field : testclass.getClass().getDeclaredFields()) {
            MockControl annotation = field.getAnnotation(MockControl.class);
            if (annotation != null) {
                control = createControl(annotation.value());
                try {
                    setField(field, testclass, control);
                } catch (EasyMockAnnotationReflectionException ex) {
                    throw new EasyMockAnnotationInitializationException("Field annotated with @MockControl must be type implements org.easymock.IMocksControl!", ex);
                }
            }
        }
        if (control == null) {
            control = EasyMock.createControl();
        }
        return control;
    }

    private static Object initializeTestedClass(Object testclass, List<MockHolder> mocks) {
        Object testedClass = null;
        boolean annotationFound = false;
        for (Field field : testclass.getClass().getDeclaredFields()) {
            Injected annotation = field.getAnnotation(Injected.class);
            if (annotation != null) {
                annotationFound = true;
                testedClass = createInstanceIfNull(field, testclass, mocks);
            } else {
                TestSubject testSubject = field.getAnnotation(TestSubject.class);
                if (testSubject != null) {
                    annotationFound = true;
                    testedClass = createInstanceIfNull(field, testclass, mocks);
                }
            }
        }
        if (annotationFound && testedClass == null) {
            throw new EasyMockAnnotationInitializationException("Failed to initilize class under test");
        }
        return testedClass;
    }

    private static List<MockHolder> processEasyMockAnnotations(Object testclass, MockFactory mockFactory) {
        Class<? extends Object> clazz = getClassOfTest(testclass);
        List<MockHolder> mocks = new LinkedList<MockHolder>();
        for (Field field : getAllFields(clazz)) {
            Mock annotation = field.getAnnotation(Mock.class);
            if (annotation != null) {
                Object mockedObject = mockIt(field, annotation.name(), annotation.value(), mockFactory, mocks);
                setField(field, testclass, mockedObject);
            } else {
                org.easymock.Mock easyMockAnnotation = field.getAnnotation(org.easymock.Mock.class);
                if (easyMockAnnotation != null) {
                    Object mockedObject = mockIt(field, easyMockAnnotation.name(), easyMockAnnotation.type(), mockFactory, mocks);
                    setField(field, testclass, mockedObject);
                }
            }
        }
        return mocks;
    }

    private static Object mockIt(Field field, String name, MockType mockType, MockFactory mockFactory, List<MockHolder> mocks) {
        Object mockedObject;
        Class<?> typeOfField = field.getType();
        mockedObject = createMock(mockFactory, typeOfField, mockType, name);
        MockHolder mock = createMockHolder(mockedObject, field, name);
        mocks.add(mock);
        return mockedObject;
    }

    private static Object createMock(MockFactory mockFactory, Class<?> typeOfField, MockType type, String name) {
        Object mockedObject;
        if (name.isEmpty()) {
            mockedObject = mockFactory.createMock(typeOfField, type);
        } else {
            mockedObject = mockFactory.createMock(typeOfField, type, name);
        }
        return mockedObject;
    }

    private static void injectToTestedClass(Object testedObject, List<MockHolder> mocks) {
        new MockInjector().addMocks(mocks).injectTo(testedObject);
    }

    private static MockHolder createMockHolder(Object mockedObject, Field field, String name) {
        MockHolder mock = new MockHolder();
        mock.setMock(mockedObject);
        mock.setSourceField(field);
        mock.setName(name);
        return mock;
    }

    private static Object createInstanceIfNull(Field field, Object testclass, List<MockHolder> mocks) {
        Object testedClass = getField(field, testclass);
        if (testedClass == null) {
            testedClass = new ClassInitializer().initialize(field.getType(), mocks);
            setField(field, testclass, testedClass);
        }
        return testedClass;
    }

    private static IMocksControl createControl(MockType value) {
        IMocksControl control;
        switch (value) {
            case NICE:
                control = EasyMock.createNiceControl();
                break;
            case STRICT:
                control = EasyMock.createStrictControl();
                break;
            case DEFAULT:
            default:
                control = EasyMock.createControl();
        }
        return control;
    }

    private EasyMockAnnotations() {
    }
}
