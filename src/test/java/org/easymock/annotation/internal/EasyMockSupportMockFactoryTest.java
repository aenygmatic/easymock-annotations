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

import org.easymock.EasyMockSupport;
import org.easymock.MockType;

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
        initializeMocks();
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
        verify(easyMockSupport);
        assertEquals(mock, actual);
    }

    private void initializeMocks() {
        mock = createMock(Object.class);
        easyMockSupport = createMock(EasyMockSupport.class);
    }
}
