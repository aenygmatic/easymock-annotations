package org.easymock.annotation.internal;

import static org.junit.Assert.assertTrue;

import org.easymock.MockType;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link StaricMockFactoryTest}.
 * <p>
 * @author Balazs Berkes
 */
public class StaricMockFactoryTest {

    private static final String MOCK_NAME = "mockname";

    private MockFactory underTest;

    @Before
    public void setUp() {
        underTest = new StaricMockFactory();
    }

    @Test
    public void testCreateMockShouldCreateDefaultMock() {
        MockType type = givenEasyMockDefaultMock();

        Object mock = underTest.createMock(MockFactory.class, type);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateNiceMock() {
        MockType type = givenEasyMockNiceMock();

        Object mock = underTest.createMock(MockFactory.class, type);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateStrictMock() {
        MockType type = givenEasyMockStrictMock();

        Object mock = underTest.createMock(MockFactory.class, type);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateDefaultMockWithName() {
        MockType type = givenEasyMockDefaultMock();

        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateNiceMockWithName() {
        MockType type = givenEasyMockNiceMock();

        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);

        assertCorrectMockTypeCreated(mock);
    }

    @Test
    public void testCreateMockShouldCreateStrictMockWithName() {
        MockType type = givenEasyMockStrictMock();

        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);

        assertCorrectMockTypeCreated(mock);
    }

    private MockType givenEasyMockDefaultMock() {
        MockType type = MockType.DEFAULT;
        return type;
    }

    private MockType givenEasyMockNiceMock() {
        MockType type = MockType.NICE;
        return type;
    }

    private MockType givenEasyMockStrictMock() {
        MockType type = MockType.STRICT;
        return type;
    }

    private void assertCorrectMockTypeCreated(Object mock) {
        assertTrue(mock instanceof MockFactory);
    }
}
