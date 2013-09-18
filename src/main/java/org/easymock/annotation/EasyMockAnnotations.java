package org.easymock.annotation;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getAllFields;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getField;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.setField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.easymock.MockType;
import org.easymock.TestSubject;

import org.easymock.annotation.exception.EasyMockAnnotationInitializationException;
import org.easymock.annotation.internal.ClassInitializer;
import org.easymock.annotation.internal.ControlledMockFactory;
import org.easymock.annotation.internal.EasyMockSupportMockFactory;
import org.easymock.annotation.internal.IMockControlFactory;
import org.easymock.annotation.internal.MockFactory;
import org.easymock.annotation.internal.MockHolder;
import org.easymock.annotation.internal.MockInjector;
import org.easymock.annotation.internal.StaricMockFactory;
import org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.UnableToWriteFieldException;

/**
 * Initialize the testclass. Scans for the {@link Mock @Mock}, {@link MockControl @MockControl} and
 * {@link Injected @Injected} annotations.
 * <p>
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
     *         EasyMockAnnotations.initializeWithMockControl(this);
     *     }
     * </pre>
     *
     * @param testclass the testclass
     */
    public static void initializeWithMockControl(Object testclass) {
        List<MockControlHolder> mockControls = injectMockControl(testclass);
        Map<String, MockFactory> controls = mapMockControlHolders(mockControls);
        List<MockHolder> mocks = processEasyMockAnnotations(testclass, controls);
        Object testedObject = initializeTestedClass(testclass, mocks);
        if (testedObject != null) {
            injectToTestedClass(testedObject, mocks);
        }
    }

    private static Map<String, MockFactory> mapMockControlHolders(List<MockControlHolder> mockControls) {
        Map<String, MockFactory> controls = new HashMap<String, MockFactory>();
        for (MockControlHolder holder : mockControls) {
            controls.put(holder.getName(), new ControlledMockFactory(holder.getMocksControl()));
        }
        return controls;
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
        Map<String, MockFactory> factories = new HashMap<String, MockFactory>();
        factories.put(null, mockFactory);
        List<MockHolder> mocks = processEasyMockAnnotations(testclass, factories);
        Object testedObject = initializeTestedClass(testclass, mocks);
        if (testedObject != null) {
            injectToTestedClass(testedObject, mocks);
        }
    }

    private static Class<?> getClassOfTest(Object testclass) throws RuntimeException {
        if (testclass == null) {
            throw new RuntimeException("Test class cannot be null!");
        }
        return testclass.getClass();
    }

    private static List<MockControlHolder> injectMockControl(Object testclass) {
        List<MockControlHolder> contols = new LinkedList<MockControlHolder>();

        for (Field field : testclass.getClass().getDeclaredFields()) {
            IMocksControl control;
            MockControl annotation = field.getAnnotation(MockControl.class);
            if (annotation != null) {
                final IMockControlFactory controlFactory = IMockControlFactory.getSingleton();
                control = controlFactory.createControl(annotation.value());
                String name = field.getName();
                try {
                    setField(field, testclass, control);
                    MockControlHolder holder = new MockControlHolder();
                    holder.setMocksControl(control);
                    holder.setName(name);
                    contols.add(holder);
                } catch (UnableToWriteFieldException ex) {
                    throw new EasyMockAnnotationInitializationException("Field annotated with @MockControl must be type implements org.easymock.IMocksControl!", ex);
                }
            }
        }
        return contols;
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

    private static List<MockHolder> processEasyMockAnnotations(Object testclass, Map<String, MockFactory> factories) {
        Class<? extends Object> clazz = getClassOfTest(testclass);
        MockFactory defaultControl = factories.values().iterator().next();
        List<MockHolder> mocks = new LinkedList<MockHolder>();
        for (Field field : getAllFields(clazz)) {
            Mock annotation = field.getAnnotation(Mock.class);
            if (annotation != null) {
                MockFactory mockFactory = factories.containsKey(annotation.control()) ? factories.get(annotation.control()) : defaultControl;
                Object mockedObject = mockIt(field, annotation.name(), annotation.value(), mockFactory, mocks);
                setField(field, testclass, mockedObject);
            } else {
                org.easymock.Mock easyMockAnnotation = field.getAnnotation(org.easymock.Mock.class);
                if (easyMockAnnotation != null) {
                    Object mockedObject = mockIt(field, easyMockAnnotation.name(), easyMockAnnotation.type(), defaultControl, mocks);
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

    private EasyMockAnnotations() {
    }

    private static class MockControlHolder {

        private IMocksControl mocksControl;
        private String name;

        public IMocksControl getMocksControl() {
            return mocksControl;
        }

        public void setMocksControl(IMocksControl mocksControl) {
            this.mocksControl = mocksControl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
