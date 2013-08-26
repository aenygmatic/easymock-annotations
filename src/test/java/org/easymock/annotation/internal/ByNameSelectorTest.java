package org.easymock.annotation.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
        mockHolder = createMock(MockHolder.class);
        holder = createMock(MockHolder.class);
        lowercasemock = createMock(MockHolder.class);

        underTest = new ByNameSelector();
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectEqualName() {
        givenMockHolderFields();
        //WHEN
        MockHolder actual = underTest.getMatchingMock("mockHoder", mocks);
        //WHEN
        assertEquals(mockHolder, actual);
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectEqualIgnoreCaseName() {
        givenMockHolderFields();

        MockHolder actual = underTest.getMatchingMock("lowerCaseMock", mocks);

        assertEquals(lowercasemock, actual);
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectMockHolderWhenItsNameContainedInFieldsName() {
        givenMockHolderFields();

        MockHolder actual = underTest.getMatchingMock("holderInName", mocks);

        assertEquals(holder, actual);
    }

    @Test
    public void testGetMatchingMockShouldReturnFirstMockWhenNoMatchingOne() {
        givenMockHolderFields();

        MockHolder actual = underTest.getMatchingMock("noSuchField", mocks);

        assertEquals(mockHolder, actual);
    }

    @Test
    public void testGetMatchingMockShouldReturnEmptyMockWhenMockListIsNull() {
        List<MockHolder> mockList = null;

        MockHolder actual = underTest.getMatchingMock("name", mockList);

        assertEquals(MockHolder.emptyMock(), actual);
    }

    @Test
    public void testGetMatchingMockShouldReturnEmptyMockWhenMockListIsEmpty() {
        List<MockHolder> mockList = Collections.EMPTY_LIST;

        MockHolder actual = underTest.getMatchingMock("name", mockList);

        assertEquals(MockHolder.emptyMock(), actual);
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
}
