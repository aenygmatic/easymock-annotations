package org.easymock.annotation.internal;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getAllFields;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getInheritanceDistance;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.setField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.easymock.annotation.exception.EasyMockAnnotationReflectionException;

/**
 * Injects the the given mocks into the target class. Mocks are injected by type and name.
 *
 * @author Balazs Berkes
 */
public class MockInjector {

    private static final int MAX_DEPTH = Integer.MAX_VALUE;

    private Set<MockHolder> mocks;
    private ByNameSelector byNameSelector = new ByNameSelector();

    /**
     * Injects the given mock into the tested object.
     * <p>
     * @param mocks mocks to inject
     * @return instance of this {@code MockInjector}.
     */
    public MockInjector addMocks(Set<MockHolder> mocks) {
        this.mocks = new HashSet<MockHolder>(mocks);
        return this;
    }

    /**
     * Injects the previously given mock into the target object.
     * <p>
     * @param target object to be injected
     * @return the target object
     */
    public Object injectTo(Object target) {
        for (Field field : getAllFields(target.getClass())) {
            injectField(field, target);
        }
        return target;
    }

    private void injectField(Field field, Object target) throws EasyMockAnnotationReflectionException {
        Class<?> type = field.getType();
        List<MockHolder> closestMocks = getClosestMocks(type);
        if (!closestMocks.isEmpty()) {
            String targetFieldName = field.getName();
            MockHolder matchingMock = byNameSelector.getMatchingMock(targetFieldName, closestMocks);
            setField(field, target, matchingMock.getMock());
        }
    }

    private List<MockHolder> getClosestMocks(Class<?> type) {
        List<MockHolder> closestMocks = new ArrayList<MockHolder>();
        int closestDist = MAX_DEPTH;
        for (MockHolder mock : mocks) {
            final int currentDist = getInheritanceDistance(mock.getMock(), type);
            if (isInstance(currentDist) && isCloserThanCurrent(currentDist, closestDist)) {
                closestDist = currentDist;
                closestMocks.clear();
                closestMocks.add(mock);
            } else if (isInstance(currentDist) && isCloseAsCurrent(currentDist, closestDist)) {
                closestMocks.add(mock);
            }
        }
        if (closestDist == MAX_DEPTH) {
            closestMocks = Collections.EMPTY_LIST;
        }
        return closestMocks;
    }

    private boolean isInstance(final int dist) {
        return dist != -1;
    }

    private boolean isCloserThanCurrent(final int dist, int inheritanceDistance) {
        return dist < inheritanceDistance;
    }

    private boolean isCloseAsCurrent(final int dist, int inheritanceDistance) {
        return dist == inheritanceDistance;
    }
}
