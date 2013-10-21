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
package integrationtest.annotations;

import static org.junit.Assert.assertEquals;

import static org.easymock.EasyMock.expect;

import org.junit.Before;
import org.junit.Test;

import org.easymock.IMocksControl;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Mock;
import org.easymock.annotation.MockControl;

/**
 * Integration test for multiple field annotated with {@link MockControl @MockContol}. Mocked objects should be created
 * by the associated {@code IMockControl}.
 * <p>
 * @author Balazs Berkes
 */
public class MockControlMultipleAnnotationIntegrationTest {

    private static final String STRING_VALUE = "dummy";

    @MockControl
    private IMocksControl control1;
    @MockControl
    private IMocksControl control2;

    @Mock(control = "control1")
    private MyObject mock1;
    @Mock(control = "control2")
    private MyObject mock2;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @Test
    public void testControlPositiveGroup1() {
        expect(mock1.stringValue()).andReturn(STRING_VALUE);

        control1.replay();

        assertEquals(STRING_VALUE, mock1.stringValue());

        control1.verify();
    }

    @Test
    public void testControlPositiveGroup2() {
        expect(mock2.stringValue()).andReturn(STRING_VALUE);

        control2.replay();

        assertEquals(STRING_VALUE, mock2.stringValue());

        control2.verify();
    }

    @Test
    public void testControlPositiveBothGroup() {
        expect(mock1.stringValue()).andReturn(STRING_VALUE);
        expect(mock2.stringValue()).andReturn(STRING_VALUE);

        control1.replay();
        control2.replay();

        assertEquals(STRING_VALUE, mock1.stringValue());
        assertEquals(STRING_VALUE, mock2.stringValue());

        control1.verify();
        control2.verify();
    }

    @Test(expected = IllegalStateException.class)
    public void testControlNegativeBothGroup() {
        expect(mock1.stringValue()).andReturn(STRING_VALUE);
        expect(mock2.stringValue()).andReturn(STRING_VALUE);

        control1.replay();

        mock1.stringValue();
        mock2.stringValue();

        control1.verify();
        control2.verify();
    }

    @Test(expected = IllegalStateException.class)
    public void testControlNegative() {
        expect(mock1.stringValue()).andReturn(STRING_VALUE);

        mock1.stringValue();

        control1.verify();
    }

    public static class MyObject {

        public String stringValue() {
            return null;
        }
    }
}
