package org.easymock.annotation.internal.selection;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getGenericParameters;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import org.easymock.annotation.internal.MockHolder;

/**
 * Selects the the mocks which has the same generic type.
 * <p>
 * @author Balazs Berkes
 */
public class ByGenericSelector implements MockSelector<Field> {

    private static MockSelector<Field> singleton;

    public static synchronized MockSelector<Field> getSingleton() {
        if (singleton == null) {
            singleton = new ByGenericSelector();
        }
        return singleton;
    }

    @Override
    public List<MockHolder> getMatchingMocks(Field selection, List<MockHolder> mocks) {
        return getMatchingMocks(selection, mocks);
    }

    @Override
    public List<MockHolder> getMatchingMocksByField(Field targetField, List<MockHolder> mocks) {
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
