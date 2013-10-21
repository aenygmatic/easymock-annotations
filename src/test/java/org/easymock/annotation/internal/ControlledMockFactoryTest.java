/*
 * Copyright 2013 Balazs Berkes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.easymock.annotation.internal;

import static org.junit.Assert.assertEquals;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Before;
import org.junit.Test;

import org.easymock.IMocksControl;
import org.easymock.MockType;

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
