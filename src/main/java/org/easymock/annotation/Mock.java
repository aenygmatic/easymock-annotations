package org.easymock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.easymock.MockType;

/**
 * Fields annotated with {@code @Mock} will be filled up with mock object after the testclass is initialized by
 * {@link EasyMockAnnotations#initialize(Object)} or {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
 *
 * @author Balazs Berkes
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mock {

    /**
     * Defines the type of mock {@link MockType}.
     * Default is {@link MockType#DEFAULT}.
     * <p>
     * @return the type of the mock
     */
    MockType value() default MockType.DEFAULT;

    /**
     * Name of the mock.
     * <p>
     * @return the name of the mock object.
     */
    String name() default "";

    /**
     * Name of the associated {@link org.easymock.IMocksControl IMocksControl}.
     * It has effect only when mocks are created via {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
     * <p>
     * @return name of the {@code IMocksControl}
     */
    String control() default "";

}
