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
        MockType type = MockType.DEFAULT;
        expect(easyMockSupport.createMock(Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, type);
        //THEN
        verify(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateNiceMock() {
        //GIVEN
        MockType type = MockType.NICE;
        expect(easyMockSupport.createNiceMock(Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, type);
        //THEN
        verify(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateStrictMock() {
        //GIVEN
        MockType type = MockType.STRICT;
        expect(easyMockSupport.createStrictMock(Thread.class)).andReturn(mock);
        replay(easyMockSupport);
        //WHEN
        underTest.createMock(Thread.class, type);
        //THEN
        verify(easyMockSupport);
    }
}
