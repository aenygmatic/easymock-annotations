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

import java.lang.reflect.Field;

import org.easymock.IMocksControl;
import org.easymock.MockType;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.MockControl;

import org.junit.Test;

/**
 * Integration test for {@link MockControl @MockContol} creation and type parameters.
 * <p>
 * @author Balazs Berkes
 */
public class MockControlAnnotationPositiveIntegrationTest {

    @MockControl(MockType.DEFAULT)
    private IMocksControl defaultControl;
    @MockControl(MockType.NICE)
    private IMocksControl niceControl;
    @MockControl(MockType.STRICT)
    private IMocksControl strictControl;
    @MockControl
    private IMocksControl control;

    @Test
    public void testiInitializeWithMockControlShouldCreateDefaultMock() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.DEFAULT, getMockType(defaultControl));
    }

    @Test
    public void testiInitializeWithMockControlShouldCreateNiceMock() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.NICE, getMockType(niceControl));
    }

    @Test
    public void testiInitializeWithMockControlShouldCreateStrictMock() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.STRICT, getMockType(strictControl));
    }

    @Test
    public void testiInitializeWithMockControlShouldCreateDefaultMockWhenNoParameterIsGiven() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.DEFAULT, getMockType(control));
    }

    private MockType getMockType(Object control) {
        for (Field f : control.getClass().getDeclaredFields()) {
            if (f.getType() == MockType.class) {
                return (MockType) getField(f, control);
            }
        }
        return null;
    }

    private Object getField(Field field, Object target) {
        Object fieldValue = null;
        field.setAccessible(true);
        try {
            fieldValue = field.get(target);
        } catch (Exception ignored) {
        }
        return fieldValue;
    }
}
