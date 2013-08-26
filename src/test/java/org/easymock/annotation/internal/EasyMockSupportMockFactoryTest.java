package org.easymock.annotation.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMockSupport;
import org.easymock.MockType;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link EasyMockSupportMockFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class EasyMockSupportMockFactoryTest {

    private static final String MOCK_NAME = "mockname";

    private EasyMockSupport easyMockSupport;
    private Object mock;

    private MockFactory underTest;

    @Before
    public void setUp() {
        mock = createMock(Object.class);
        easyMockSupport = createMock(EasyMockSupport.class);

        underTest = new EasyMockSupportMockFactory(easyMockSupport);
    }

    @Test
    public void testCreateMockShouldCreateMock() {
        givenEasyMockSupportCreatesMock();

        Object actual = underTest.createMock(Object.class, MockType.DEFAULT);

        assertEasyMockSupportMockCreated(actual);
    }

    @Test
    public void testCreateMockShouldCreateNiceMock() {
        givenEasyMockSupportCreatesNiceMock();

        Object actual = underTest.createMock(Object.class, MockType.NICE);

        assertEasyMockSupportMockCreated(actual);
    }

    @Test
    public void testCreateMockShouldCreateStrictMock() {
        givenEasyMockSupportCreatesStrictMock();

        Object actual = underTest.createMock(Object.class, MockType.STRICT);

        assertEasyMockSupportMockCreated(actual);
    }

    @Test
    public void testCreateMockShouldCreateMockWithName() {
        givenEasyMockSupportCreatesNamedMock();

        Object actual = underTest.createMock(Object.class, MockType.DEFAULT, MOCK_NAME);

        assertEasyMockSupportMockCreated(actual);
    }

    @Test
    public void testCreateMockShouldCreateNiceMockWithName() {
        givenEasyMockSupportCreatesNamedNiceMock();

        Object actual = underTest.createMock(Object.class, MockType.NICE, MOCK_NAME);

        assertEasyMockSupportMockCreated(actual);
    }

    @Test
    public void testCreateMockShouldCreateStrictMockWithName() {
        givenEasyMockSupportCreatesNamedStrictMock();

        Object actual = underTest.createMock(Object.class, MockType.STRICT, MOCK_NAME);

        assertEasyMockSupportMockCreated(actual);
    }

    private void givenEasyMockSupportCreatesMock() {
        expect(easyMockSupport.createMock(Object.class)).andReturn(mock);
        replay(easyMockSupport);
    }

    private void givenEasyMockSupportCreatesNiceMock() {
        expect(easyMockSupport.createNiceMock(Object.class)).andReturn(mock);
        replay(easyMockSupport);
    }

    private void givenEasyMockSupportCreatesStrictMock() {
        expect(easyMockSupport.createStrictMock(Object.class)).andReturn(mock);
        replay(easyMockSupport);
    }

    private void givenEasyMockSupportCreatesNamedNiceMock() {
        expect(easyMockSupport.createNiceMock(MOCK_NAME, Object.class)).andReturn(mock);
        replay(easyMockSupport);
    }

    private void givenEasyMockSupportCreatesNamedMock() {
        expect(easyMockSupport.createMock(MOCK_NAME, Object.class)).andReturn(mock);
        replay(easyMockSupport);
    }

    private void givenEasyMockSupportCreatesNamedStrictMock() {
        expect(easyMockSupport.createStrictMock(MOCK_NAME, Object.class)).andReturn(mock);
        replay(easyMockSupport);
    }

    private void assertEasyMockSupportMockCreated(Object actual) {
        //THEN
        verify(easyMockSupport);
        assertEquals(mock, actual);
    }
}
