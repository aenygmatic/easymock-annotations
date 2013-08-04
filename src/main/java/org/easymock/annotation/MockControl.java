package org.easymock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields annotated with {@code @MockControl} will be filled up with {@link org.easymock.IMocksControl IMocksControl}
 * object after the testclass is initialized by {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
 *
 * @author Balazs Berkes
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MockControl {
}
