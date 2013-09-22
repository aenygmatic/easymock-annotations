package org.easymock.annotation;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getAllDeclaredFields;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getField;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.setField;
import static org.easymock.annotation.utils.EasyMockAnnotationValidationUtils.assertNotNull;
import static org.easymock.annotation.utils.EasyMockAnnotationValidationUtils.isNull;
import static org.easymock.annotation.utils.EasyMockAnnotationValidationUtils.notNull;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.easymock.MockType;
import org.easymock.TestSubject;

import org.easymock.annotation.exception.EasyMockAnnotationInitializationException;
import org.easymock.annotation.internal.ClassInitializer;
import org.easymock.annotation.internal.IMockControlFactory;
import org.easymock.annotation.internal.MockHolder;
import org.easymock.annotation.internal.MockInjector;

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

        private final IMockControlFactory controlFactory = IMockControlFactory.getSingleton();
        private final NavigableMap<String, IMocksControl> namedControls = new TreeMap<String, IMocksControl>();
        private final List<MockHolder> mocks = new LinkedList<MockHolder>();
        private final MockInjector mockInjector = new MockInjector(mocks);

        private Object testclass;

        private void initialize(Object testclass) {
            this.testclass = testclass;
            initializeMockControls();
            initializeMocks();
            initializeTestedClass();
        }

        private void initializeMockControls() {
            for (Field field : getAllDeclaredFields(testclass.getClass())) {
                if (field.isAnnotationPresent(MockControl.class)) {
                    createAndInjectControl(field);
                }
            }
        }

        private void createAndInjectControl(Field field) {
            if (field.getType() != IMocksControl.class) {
                throw new EasyMockAnnotationInitializationException("Field annotated with @MockControl must be type of org.easymock.IMocksControl!");
            }
            MockControl annotation = field.getAnnotation(MockControl.class);
            IMocksControl control = controlFactory.createControl(annotation.value());
            injectToTestclass(field, control);
            namedControls.put(field.getName(), control);
        }

        private void initializeMocks() {
            FallbackMockHolderFactory fallbackFactory = new FallbackMockHolderFactory(namedControls, testclass);
            for (Field field : getAllDeclaredFields(testclass.getClass())) {
                createAndInjectMock(field, fallbackFactory);
            }
        }

        private void initializeTestedClass() {
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
                testedClass = new ClassInitializer().initialize(field.getType(), mocks);
                injectToTestclass(field, testedClass);
            }
            return testedClass;
        }

        private void createAndInjectMock(Field field, FallbackMockHolderFactory fallbackFactory) {
            Mock annotation = field.getAnnotation(Mock.class);
            if (notNull(annotation)) {
                processMockAnnotation(fallbackFactory, field, annotation.name(), annotation.value(), annotation.control());
            } else {
                processEasymockAnnotationIfPresented(field, fallbackFactory);
            }
        }

        private void processMockAnnotation(FallbackMockHolderFactory fallbackFactory, Field field, String name, MockType type, String control) {
            MockHolder mock = fallbackFactory.createMock(field, name, type, control);
            mocks.add(mock);
            injectToTestclass(field, mock.getMock());
        }

        private void processEasymockAnnotationIfPresented(Field field, FallbackMockHolderFactory fallbackFactory) {
            org.easymock.Mock easyMockAnnotation = field.getAnnotation(org.easymock.Mock.class);
            if (notNull(easyMockAnnotation)) {
                processMockAnnotation(fallbackFactory, field, easyMockAnnotation.name(), easyMockAnnotation.type(), "");
            }
        }

        private void injectToTestclass(Field field, Object control) {
            setField(field, testclass, control);
        }
    }
}
