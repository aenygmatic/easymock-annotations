/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.IMocksControl;
import org.easymock.MockType;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ControlledMockFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class ControlledMockFactoryTest {

    private static final String MOCK_NAME = "mockname";

    private IMocksControl control;
    private Object mock;

    private MockFactory underTest;

    @Before
    public void setUp() {
        initializeMocks();
        underTest = new ControlledMockFactory(control);
    }

    @Test
    public void testCreateMockShouldCreateMockRegardlessOfType() {
        givenIMocksControlCreatesMock();

        Object actual = underTest.createMock(Object.class, MockType.DEFAULT);

        assertIMockControlMockCreated(actual);
    }

    @Test
    public void testCreateMockShouldCreateMockWithName() {
        givenIMocksControlCreatesNamedMock();

        Object actual = underTest.createMock(Object.class, MockType.DEFAULT, MOCK_NAME);

        assertIMockControlMockCreated(actual);
    }

    private void givenIMocksControlCreatesMock() {
        expect(control.createMock(Object.class)).andReturn(mock);
        replay(control);
    }

    private void givenIMocksControlCreatesNamedMock() {
        expect(control.createMock(MOCK_NAME, Object.class)).andReturn(mock);
        replay(control);
    }

    private void assertIMockControlMockCreated(Object actual) {
        verify(control);
        assertEquals(mock, actual);
    }

    private void initializeMocks() {
        mock = createMock(Object.class);
        control = createMock(IMocksControl.class);
    }
}
