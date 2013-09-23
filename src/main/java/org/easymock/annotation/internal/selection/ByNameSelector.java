package org.easymock.annotation.internal.selection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.easymock.annotation.internal.MockHolder;

/**
 * Selects one mock which has matching name decided by the given {@link SelectionStrategy}.
 * <p>
 * @author Balazs Berkes
 */
public class ByNameSelector implements MockSelector<String> {

    public static final SelectionStrategy NAME_EQUALS_STRATEGY = new NameEqualsStrategy();
    public static final SelectionStrategy NAME_EQUALS_IGNORE_CASE_STRATEGY = new NameEqualsIgnoreCaseStrategy();
    public static final SelectionStrategy NAME_CONTAINS_STRATEGY = new NameContainsStrategy();

    private final static List<SelectionStrategy> strategies = loadStrategies(NAME_EQUALS_STRATEGY, NAME_EQUALS_IGNORE_CASE_STRATEGY, NAME_CONTAINS_STRATEGY);

    private static MockSelector<String> singleton;

    public static void overrideStrategy(SelectionStrategy... strategies) {
        synchronized (ByNameSelector.strategies) {
            ByNameSelector.strategies.clear();
            ByNameSelector.strategies.addAll(Arrays.asList(strategies));
        }
    }

    public static synchronized MockSelector<String> getSingleton() {
        if (singleton == null) {
            singleton = new ByNameSelector();
        }
        return singleton;
    }

    /**
     * Select a the matching mock from the given mocks according to the selection strategy.
     * <p>
     * Default strategy order
     * <ul>
     * <li>Equals</li>
     * <li>Equals ignore case</li>
     * <li>One contains the other</li>
     * </ul>
     * <p>
     * @param targetName name of the field the mock will be injected
     * @param mocks list of {@link MockHolder} of the possible mock objects
     * @return return one mock if it matches to the {@link SelectionStrategy SelectionStrategies}. If
     * no match found the first element of the list will be returned. If the given list is empty or
     * {@code null} {@link MockHolder#emptyMock()} will be returned.
     */
    @Override
    public List<MockHolder> getMatchingMocks(String targetName, List<MockHolder> mocks) {
        List<MockHolder> matchingMocks = new LinkedList<MockHolder>();
        MockHolder matchingMock = null;

        int highestPriority = strategies.size() + 1;
        for (MockHolder mock : mocks) {
            int currentPrio = getPriorityLevel(targetName, mock);
            if (currentPrio < highestPriority) {
                highestPriority = currentPrio;
                matchingMock = mock;
            }
        }
        addMatchIfFound(matchingMock, matchingMocks);

        return matchingMocks;
    }

    private int getPriorityLevel(String targetName, MockHolder mock) {
        int currentPrio = 0;
        for (SelectionStrategy strategy : strategies) {
            if (strategy.isMatching(targetName, mock.getSourceName())) {
                break;
            }
            currentPrio++;
        }
        return currentPrio;
    }

    private void addMatchIfFound(MockHolder matchingMock, List<MockHolder> matchingMocks) {
        if (matchingMock != null) {
            matchingMocks.add(matchingMock);
        }
    }

    private static List<SelectionStrategy> loadStrategies(SelectionStrategy... selectionStrategies) {
        return new CopyOnWriteArrayList<SelectionStrategy>(Arrays.asList(selectionStrategies));
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
