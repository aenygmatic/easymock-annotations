package org.easymock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields annotated with {@code @Injected} will be injected with mocks created by
 * {@link org.easymock.annotation.Mock @Mock} annotation after the testclass is initialized by
 * {@link EasyMockAnnotations#initialize(Object)} or {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
 *
 * <p>
 * Injection priority
 * <ul>
 * <li>If two or more mock object can be injected to a field the closest by inheritance will be choosen</li>
 * <li>If two or more mock object has the same inheritance distance the mock will be injected by name (equals/equals
 * lowercase)</li>
 * <li>If the mock cannot be selected by these rules the first mock will be choosen</li>
 * </ul>
 * The same mock object can be used multiple times.
 *
 * @author Balazs Berkes
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Injected {
}
