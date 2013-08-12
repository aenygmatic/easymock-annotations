package org.easymock.annotation.internal;

import java.util.Arrays;
import java.util.List;

/**
 * Selects one mock which has matching name decided by the given {@link SelectionStrategy}.
 * <p>
 * @author Balazs Berkes
 */
public class ByNameSelector {

    public static final SelectionStrategy NAME_EQUALS_STRATEGY = new NameEqualsStrategy();
    public static final SelectionStrategy NAME_EQUALS_IGNORE_CASE_STRATEGY = new NameEqualsIgnoreCaseStrategy();
    public static final SelectionStrategy NAME_CONTAINS_STRATEGY = new NameContainsStrategy();

    private List<SelectionStrategy> strategies = Arrays.asList(NAME_EQUALS_STRATEGY, NAME_EQUALS_IGNORE_CASE_STRATEGY, NAME_CONTAINS_STRATEGY);

    /**
     * Selecta the matching mock from the given mocks according to the selection strategy.
     * <p>
     * Default stategy order
     * <ul>
     * <li>Equals</li>
     * <li>Equals ignore case</li>
     * <li>One conatains the other</li>
     * </ul>
     * <p>
     * @param targetName name of the field the mock will be injected
     * @param mocks list of {@link MockHolder} of the possible mock objects
     * @return return one mock if it matches to the {@link SelectionStrategy SelectionStategies}. If
     * no match found the first element of the list will be returned. If the given list is empty or
     * {@code null} {@link MockHolder#emptyMock()} will be returned.
     */
    public MockHolder getMatchingMock(String targetName, List<MockHolder> mocks) {
        MockHolder matchingMock = null;
        if (notEmpty(mocks)) {
            int highestPriority = strategies.size() + 1;
            for (MockHolder mock : mocks) {
                int currentPrio = getPriorityLevel(targetName, mock);
                if (currentPrio < highestPriority) {
                    highestPriority = currentPrio;
                    matchingMock = mock;
                }
            }
            matchingMock = getFirstWhenNoMatchByName(matchingMock, mocks);
        } else {
            matchingMock = MockHolder.emptyMock();
        }
        return matchingMock;
    }

    public ByNameSelector overrideStategy(SelectionStrategy... stategies) {
        strategies = Arrays.asList(stategies);
        return this;
    }

    private int getPriorityLevel(String targetName, MockHolder mock) {
        int currentPrio = 0;
        for (SelectionStrategy stategy : strategies) {
            if (stategy.isMatching(targetName, mock.getSourceName())) {
                break;
            }
            currentPrio++;
        }
        return currentPrio;
    }

    private MockHolder getFirstWhenNoMatchByName(MockHolder matchingMock, List<MockHolder> mocks) {
        MockHolder mock;
        if (matchingMock == null) {
            mock = mocks.get(0);
        } else {
            mock = matchingMock;
        }
        return mock;
    }

    private boolean notEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static interface SelectionStrategy {

        /**
         * Determines that the target fields name matched to the source field name are acceptible to its rule.
         * <p>
         * @param targetName name of the field in the class under test
         * @param mockSourceName name of the field in the test class
         * @return retuns {@code true} if the names are matching to this rule otherwise false
         */
        boolean isMatching(String targetName, String mockSourceName);
    }

    public static class NameEqualsStrategy implements SelectionStrategy {

        @Override
        public boolean isMatching(String targetName, String mockSourceName) {
            return targetName.equals(mockSourceName);
        }
    }

    public static class NameEqualsIgnoreCaseStrategy implements SelectionStrategy {

        @Override
        public boolean isMatching(String targetName, String mockSourceName) {
            return targetName.equalsIgnoreCase(mockSourceName);
        }
    }

    public static class NameContainsStrategy implements SelectionStrategy {

        @Override
        public boolean isMatching(String targetName, String mockSourceName) {
            return targetName.contains(mockSourceName) || mockSourceName.contains(targetName);
        }
    }
}
