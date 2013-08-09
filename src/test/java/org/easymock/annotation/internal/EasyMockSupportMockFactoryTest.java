package org.easymock.annotation.internal;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.easymock.EasyMockSupport;
import org.easymock.MockType;
import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Mock;

/**
 * Unit test for {@link EasyMockSupportMockFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class EasyMockSupportMockFactoryTest {

    private static final String MOCK_NAME = "mockname";

    @Mock
    private EasyMockSupport easyMockSupport;
    @Mock
    private Thread mock;

    private MockFactory underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
        underTest = new EasyMockSupportMockFactory(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateMock() {
        //GIVEN
        expect(easyMockSupport.createMock(Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, MockType.DEFAULT);
        //THEN
        verify(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateNiceMock() {
        //GIVEN
        expect(easyMockSupport.createNiceMock(Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, MockType.NICE);
        //THEN
        verify(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateStrictMock() {
        //GIVEN
        expect(easyMockSupport.createStrictMock(Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, MockType.STRICT);
        //THEN
        verify(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateMockWithName() {
        //GIVEN
        expect(easyMockSupport.createMock(MOCK_NAME, Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, MockType.DEFAULT, MOCK_NAME);
        //THEN
        verify(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateNiceMockWithName() {
        //GIVEN
        expect(easyMockSupport.createNiceMock(MOCK_NAME, Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, MockType.NICE, MOCK_NAME);
        //THEN
        verify(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateStrictMockWithName() {
        //GIVEN
        expect(easyMockSupport.createStrictMock(MOCK_NAME, Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, MockType.STRICT, MOCK_NAME);
        //THEN
        verify(easyMockSupport);
    }
}
