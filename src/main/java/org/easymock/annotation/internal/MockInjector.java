package org.easymock.annotation.internal;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getAllDeclaredFields;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.setField;

import java.lang.reflect.Field;
import java.util.List;

import org.easymock.annotation.exception.EasyMockAnnotationReflectionException;
import org.easymock.annotation.internal.selection.ByGenericSelector;
import org.easymock.annotation.internal.selection.ByNameSelector;
import org.easymock.annotation.internal.selection.ByTypeSelector;
import org.easymock.annotation.internal.selection.MockSelector;

/**
 * Injects the the given mocks into the target class. Mocks are injected by type and name.
 *
 * @author Balazs Berkes
 */
public class MockInjector {

    private MockSelector<String> byNameSelector = ByNameSelector.getSingleton();
    private MockSelector<Class<?>> byTypeSelector = ByTypeSelector.getSingleton();
    private MockSelector<Field> byGenericSelector = ByGenericSelector.getSingleton();
    private List<MockHolder> mocks;

    public MockInjector(List<MockHolder> mocks) {
        this.mocks = mocks;
    }

    /**
     * Injects the previously given mock into the target object.
     * <p>
     * @param target object to be injected
     * @return the target object
     */
    public Object injectTo(Object target) {
        for (Field field : getAllDeclaredFields(target.getClass())) {
            injectField(field, target);
        }
        return target;
    }

    private void injectField(Field field, Object target) throws EasyMockAnnotationReflectionException {
        List<MockHolder> closestByTypeMocks = byTypeSelector.getMatchingMocks(field.getType(), mocks);
        if (notEmpty(closestByTypeMocks)) {
            List<MockHolder> genericlyEqualsMocks = byGenericSelector.getMatchingMocks(field, closestByTypeMocks);
            if (notEmpty(genericlyEqualsMocks)) {
                List<MockHolder> matchingMocks = byNameSelector.getMatchingMocks(field.getName(), genericlyEqualsMocks);
                MockHolder matchingMock = getFirstIfAny(matchingMocks);
                setField(field, target, matchingMock.getMock());
            }
        }
    }

    private MockHolder getFirstIfAny(List<MockHolder> list) {
        MockHolder result;
        if (list.isEmpty()) {
            result = MockHolder.emptyMock();
        } else {
            result = list.get(0);
        }
        return result;

    }

    private boolean notEmpty(List<?> list) {
        return !list.isEmpty();
    }
}
