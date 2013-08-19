package org.easymock.annotation.internal;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getGenericParameters;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Selects the the mocks which has the same generic type.
 * <p>
 * @author Balazs Berkes
 */
public class ByGenericSelector {

    /**
     * Selects the mocks which has the same generic parameter as the target field.
     * This method assumes that the given lost of mock contains only instances of the target field.
     * <p>
     * @param targetField the target field the mocks will be injected
     * @param mocks list of mocks
     * @return the mock which are parameterized with the same classes
     */
    public List<MockHolder> getMatchingMocks(Field targetField, List<MockHolder> mocks) {
        List<MockHolder> matchingMocks = new LinkedList<MockHolder>();
        List<Type> targetGenerics = getGenericParameters(targetField);
        for (MockHolder mockHolder : mocks) {
            List<Type> sourceGenerics = mockHolder.getGenericParameters();
            if (genericParametersAreMatching(targetGenerics, sourceGenerics)) {
                matchingMocks.add(mockHolder);
            }
        }
        return matchingMocks;
    }

    private boolean genericParametersAreMatching(List<Type> targetGenerics, List<Type> sourceGenerics) {
        return orderlyEquals(sourceGenerics, targetGenerics);
    }

    private boolean orderlyEquals(List<Type> left, List<Type> right) {
        boolean equals = false;
        if (left.size() == right.size()) {
            equals = true;
            for (int i = 0; i < right.size(); i++) {
                Type leftElement = left.get(i);
                Type rightElement = right.get(i);
                if (!leftElement.equals(rightElement)) {
                    equals = false;
                }
            }
        }
        return equals;
    }
}
