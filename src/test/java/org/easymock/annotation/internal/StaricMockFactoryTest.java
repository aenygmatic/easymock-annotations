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
        //GIVEN
        MockType type = MockType.DEFAULT;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

    @Test
    public void testCreateMockShouldCreateNiceMock() {
        //GIVEN
        MockType type = MockType.NICE;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

    @Test
    public void testCreateMockShouldCreateStrictMock() {
        //GIVEN
        MockType type = MockType.STRICT;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

    @Test
    public void testCreateMockShouldCreateDefaultMockWithName() {
        //GIVEN
        MockType type = MockType.DEFAULT;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

    @Test
    public void testCreateMockShouldCreateNiceMockWithName() {
        //GIVEN
        MockType type = MockType.NICE;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

    @Test
    public void testCreateMockShouldCreateStrictMockWithName() {
        //GIVEN
        MockType type = MockType.STRICT;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type, MOCK_NAME);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }
}
