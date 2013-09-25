package org.easymock.annotation.internal.selection;

import java.lang.reflect.Field;
import java.util.List;

import org.easymock.annotation.internal.MockHolder;

/**
 * Provides unified interface for selecting mock according to specific rules.
 * <p>
 * @author Balazs Berkes
 * @param <T> type of object which the mock will be compared to
 */
public interface MockSelector<T> {

    /**
     * Selects the mock according to the rules of implementation.
     * <p>
     * @param selection the reference to compare in the implemented rule
     * @param mocks original list of mocks
     * @return selected list of mocks
     */
    List<MockHolder> getMatchingMocks(T selection, List<MockHolder> mocks);

    /**
     * Selects the mock according to the rules of implementation.
     * <p>
     * @param field with and attribute which will be the reference to compare in the implemented rule
     * @param mocks original list of mocks
     * @return selected list of mocks
     */
    List<MockHolder> getMatchingMocksByField(Field field, List<MockHolder> mocks);

}
