package org.easymock.annotation.internal.selection;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.internal.MockHolder;

/**
 * Unit test for {@link ByNameSelector}.
 * <p>
 * @author Balazs Berkes
 */
public class ByNameSelectorTest {

    private List<MockHolder> mocks;
    private MockHolder mockHolder;
    private MockHolder holder;
    private MockHolder lowercasemock;

    private ByNameSelector underTest;

    @Before
    public void setUp() {
        initializeMocks();
        underTest = new ByNameSelector();
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectEqualName() {
        givenMockHolderFields();

        List<MockHolder> actual = underTest.getMatchingMocks("mockHoder", mocks);

        assertEquals(mockHolder, firstElementOf(actual));
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectEqualIgnoreCaseName() {
        givenMockHolderFields();

        List<MockHolder> actual = underTest.getMatchingMocks("lowerCaseMock", mocks);

        assertEquals(lowercasemock, firstElementOf(actual));
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectMockHolderWhenItsNameContainedInFieldsName() {
        givenMockHolderFields();

        List<MockHolder> actual = underTest.getMatchingMocks("holderInName", mocks);

        assertEquals(holder, firstElementOf(actual));
    }

    @Test
    public void testGetMatchingMockShouldReturnFirstMockWhenNoMatchingOne() {
        givenMockHolderFields();

        List<MockHolder> actual = underTest.getMatchingMocks("noSuchField", mocks);

        assertEquals(mockHolder, firstElementOf(actual));
    }

    @Test
    public void testGetMatchingMockShouldReturnEmptyMocksWhenMockListIsEmpty() {
        List<MockHolder> mockList = Collections.EMPTY_LIST;

        List<MockHolder> actual = underTest.getMatchingMocks("name", mockList);

        assertTrue(actual.isEmpty());
    }

    private void givenMockHolderFields() {
        mocks = new LinkedList<MockHolder>();
        expect(mockHolder.getSourceName()).andStubReturn("mockHoder");
        expect(holder.getSourceName()).andStubReturn("holder");
        expect(lowercasemock.getSourceName()).andStubReturn("lowercasemock");
        mocks.add(mockHolder);
        mocks.add(holder);
        mocks.add(lowercasemock);
        replay(mockHolder, holder, lowercasemock);
    }

    private void initializeMocks() {
        mockHolder = createMock(MockHolder.class);
        holder = createMock(MockHolder.class);
        lowercasemock = createMock(MockHolder.class);
    }

    private MockHolder firstElementOf(List<MockHolder> actual) {
        return actual.get(0);
    }
}
