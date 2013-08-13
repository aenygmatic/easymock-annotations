package org.easymock.annotation.internal;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

/**
 * Unit test for {@link ByNameSelector}.
 * <p>
 * @author Balazs Berkes
 */
public class ByNameSelectorTest {

    private List<MockHolder> mocks;
    @Mock
    private MockHolder mockHolder;
    @Mock
    private MockHolder holder;
    @Mock
    private MockHolder lowercasemock;

    @Injected
    private ByNameSelector underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectEqualName() {
        //GIVEN
        initializeMockHolderFields();
        //WHEN
        MockHolder actual = underTest.getMatchingMock("mockHoder", mocks);
        //WHEN
        assertEquals(mockHolder, actual);
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectEqualIgnoreCaseName() {
        //GIVEN
        initializeMockHolderFields();
        //WHEN
        MockHolder actual = underTest.getMatchingMock("lowerCaseMock", mocks);
        //WHEN
        assertEquals(lowercasemock, actual);
    }

    @Test
    public void testGetMatchingMockDefaultStategyShouldSelectMockHolderWhenItsNameContainedInFieldsName() {
        //GIVEN
        initializeMockHolderFields();
        //WHEN
        MockHolder actual = underTest.getMatchingMock("holderInName", mocks);
        //WHEN
        assertEquals(holder, actual);
    }

    @Test
    public void testGetMatchingMockShouldReturnFirstMockWhenNoMatchingOne() {
        //GIVEN
        initializeMockHolderFields();
        //WHEN
        MockHolder actual = underTest.getMatchingMock("noSuchField", mocks);
        //WHEN
        assertEquals(mockHolder, actual);
    }

    @Test
    public void testGetMatchingMockShouldReturnEmptyMockWhenMockListIsNull() {
        //GIVEN
        List<MockHolder> mockList = null;
        //WHEN
        MockHolder actual = underTest.getMatchingMock("name", mockList);
        //WHEN
        assertEquals(MockHolder.emptyMock(), actual);
    }

    @Test
    public void testGetMatchingMockShouldReturnEmptyMockWhenMockListIsEmpty() {
        //GIVEN
        List<MockHolder> mockList = Collections.EMPTY_LIST;
        //WHEN
        MockHolder actual = underTest.getMatchingMock("name", mockList);
        //WHEN
        assertEquals(MockHolder.emptyMock(), actual);
    }

    private void initializeMockHolderFields() {
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
