package org.easymock.annotation.internal;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getInheritanceDistance;
import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.setField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Injects the the given mocks into the target class. Mocks are injected by type and name.
 *
 * @author Balazs Berkes
 */
public class MockInjector {

    public static final int MAX_DEPTH = Integer.MAX_VALUE;
    private Set<MockHolder> mocks;

    /**
     * Injects the given mock into the tested object.
     *
     * @param mocks mocks to inject
     * @return instance of the {@code MockInjector}.
     */
    public MockInjector injectMocks(Set<MockHolder> mocks) {
        this.mocks = new HashSet<MockHolder>(mocks);
        return this;
    }

    /**
     * Injects the previously given mock into the target object.
     *
     * @param target object to be injected
     * @return the target object
     */
    public Object injectTo(Object target) {
        for (Field field : target.getClass().getDeclaredFields()) {
            Class<?> type = field.getType();
            String targetFieldName = field.getName();
            final List<MockHolder> closestMocks = getClosestMocks(type);
            Object closestMock = selectByName(targetFieldName, closestMocks);
            setField(field, target, closestMock);
        }
        return target;
    }

    private List<MockHolder> getClosestMocks(Class<?> type) {
        List<MockHolder> closestMocks = new ArrayList<MockHolder>();
        int inheritanceDistance = MAX_DEPTH;
        for (MockHolder mock : mocks) {
            int dist = getInheritanceDistance(mock.getMock(), type);
            if (dist != -1 && dist < inheritanceDistance) {
                inheritanceDistance = dist;
                closestMocks.clear();
                closestMocks.add(mock);
            } else if (dist != -1 && dist == inheritanceDistance) {
                closestMocks.add(mock);
            }
        }
        if (inheritanceDistance == MAX_DEPTH) {
            closestMocks = Collections.EMPTY_LIST;
        }
        return closestMocks;
    }

    private Object selectByName(String targetName, List<MockHolder> mocks) {
        Object matchingMock = null;
        if (mocks.size() > 0) {
            for (MockHolder mock : mocks) {
                final String sourceName = mock.getSourceName();
                if (targetName.equals(sourceName)) {
                    matchingMock = mock.getMock();
                    break;
                } else if (targetName.equalsIgnoreCase(sourceName)) {
                    if (matchingMock == null) {
                        matchingMock = mock.getMock();
                    }
                }
            }
        }
        matchingMock = injectFirstWhenNoMatchByName(matchingMock, mocks);
        return matchingMock;
    }

    private Object injectFirstWhenNoMatchByName(Object matchingMock, List<MockHolder> mocks) {
        if (matchingMock == null && !mocks.isEmpty()) {
            matchingMock = mocks.get(0).getMock();
        }
        return matchingMock;
    }
}
