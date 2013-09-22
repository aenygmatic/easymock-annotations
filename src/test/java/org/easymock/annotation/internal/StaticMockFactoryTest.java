package org.easymock.annotation.internal;

import static org.junit.Assert.assertTrue;

import org.easymock.MockType;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link StaticMockFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class StaticMockFactoryTest {

    private static final String MOCK_NAME = "mockname";

    private MockType type;
    private MockFactory underTest;

    @Before
    public void setUp() {
        underTest = new StaticMockFactory();
    }

    @Test
    public void testCreateMockShouldCreateDefaultMock() {
        givenEasyMockDefaultMock();

        Object mock = underTest.createMock(MockFactory.class, type);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateNiceMock() {
        givenEasyMockNiceMock();

        Object mock = underTest.createMock(MockFactory.class, type);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateStrictMock() {
        givenEasyMockStrictMock();

        Object mock = underTest.createMock(MockFactory.class, type);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateDefaultMockWithName() {
        givenEasyMockDefaultMock();

        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateNiceMockWithName() {
        givenEasyMockNiceMock();

        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateStrictMockWithName() {
        givenEasyMockStrictMock();

        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);

        assertCorrectMockTypeCreated(mock);
    }

    private void givenEasyMockDefaultMock() {
        type = MockType.DEFAULT;
    }

    private void givenEasyMockNiceMock() {
        type = MockType.NICE;
    }

    private void givenEasyMockStrictMock() {
        type = MockType.STRICT;
    }

    private void assertCorrectMockTypeCreated(Object mock) {
        assertTrue(mock instanceof MockFactory);
    }
}
