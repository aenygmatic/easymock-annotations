package org.easymock.annotation.internal.selection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.internal.MockHolder;

/**
 * Unit test for {@link ByTypeSelector}.
 * <p>
 * @author Balazs Berkes
 */
public class ByTypeSelectorTest {

    private MockHolder arrayListMock;
    private MockHolder linkedListMock;
    private MockHolder hashSetMock;
    private List<MockHolder> mocks;

    private ByTypeSelector underTest;

    @Before
    public void setUp() {
        initilaizeMockHolders();
        underTest = new ByTypeSelector();
    }

    @Test
    public void testSelectShouldReturnEmptyListWhenNoMatchByType() {
        givenMocksOf(arrayListMock, linkedListMock, hashSetMock);

        List<MockHolder> selectedMocks = underTest.select(Map.class, mocks);

        assertTrue(selectedMocks.isEmpty());
    }

    @Test
    public void testSelectShouldReturnListOfMockWhenMoreMocksAreAtSameInheritanceLevel() {
        givenMocksOf(arrayListMock, linkedListMock, hashSetMock);

        List<MockHolder> selectedMocks = underTest.select(Collection.class, mocks);

        assertContainsOnly(selectedMocks, arrayListMock, hashSetMock);
    }

    @Test
    public void testSelectShouldReturnOneMockWhenThereIsOneClosest() {
        givenMocksOf(arrayListMock, linkedListMock, hashSetMock);

        List<MockHolder> selectedMocks = underTest.select(List.class, mocks);

        assertContainsOnly(selectedMocks, arrayListMock);
    }

    private void initilaizeMockHolders() {
        arrayListMock = new MockHolder();
        arrayListMock.setMock(new ArrayList<Object>());

        linkedListMock = new MockHolder();
        linkedListMock.setMock(new LinkedList<Object>());

        hashSetMock = new MockHolder();
        hashSetMock.setMock(new HashSet<Object>());
    }

    private void givenMocksOf(MockHolder... mocks) {
        this.mocks = Arrays.asList(mocks);
    }

    private void assertContainsOnly(List<MockHolder> actual, MockHolder... expectations) {
        assertTrue(actual.containsAll(Arrays.asList(expectations)));
        assertEquals(expectations.length, actual.size());
    }

}
