package org.easymock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.easymock.MockType;

/**
 * Fields annotated with {@code @MockControl} will be filled up with {@link org.easymock.IMocksControl IMocksControl}
 * object after the testclass is initialized by {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
 *
 * @author Balazs Berkes
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MockControl {

    /**
     * Defines the type of mocks created by this control {@link MockType}.
     * Default is {@link MockType#DEFAULT}.
     * </p>
     * This setup of the {@code @Mock} annotation will be overriden by this setup if created by control.
     *
     * @return the type of the mock
     */
    MockType value() default MockType.DEFAULT;
}
