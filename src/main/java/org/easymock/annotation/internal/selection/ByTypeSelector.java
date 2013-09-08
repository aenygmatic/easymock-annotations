package org.easymock.annotation.internal.selection;

import static org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.getInheritanceDistance;

import java.util.LinkedList;
import java.util.List;

import org.easymock.annotation.internal.MockHolder;

/**
 * Selects the the mocks which are the closest.
 * <p>
 * @author Balazs Berkes
 */
public class ByTypeSelector implements MockSelector<Class<?>> {

    private static final int MAX_DEPTH = Integer.MAX_VALUE;

    @Override
    public List<MockHolder> getMatchingMocks(Class<?> selection, List<MockHolder> mocks) {
        List<MockHolder> closestMocks = new LinkedList<MockHolder>();
        int closestDist = MAX_DEPTH;
        for (MockHolder mock : mocks) {
            final int currentDist = getInheritanceDistance(mock.getMock(), selection);
            if (isInstance(currentDist) && isCloserThanCurrent(currentDist, closestDist)) {
                closestDist = currentDist;
                closestMocks.clear();
                closestMocks.add(mock);
            } else if (isInstance(currentDist) && isCloseAsCurrent(currentDist, closestDist)) {
                closestMocks.add(mock);
            }
        }

        return clearIfNoInstanceFound(closestDist, closestMocks);
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

    private List<MockHolder> clearIfNoInstanceFound(int closestDist, List<MockHolder> closestMocks) {
        if (closestDist == MAX_DEPTH) {
            closestMocks.clear();
        }
        return closestMocks;
    }
}
