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
package org.easymock.annotation.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for {@link EasyMockAnnotationValidationUtils}.
 * <p>
 * @author Balazs Berkes
 */
public class EasyMockAnnotationValidationUtilsTest {

    public static final String ASSERT_MESSAGE = "Assert message";

    @Test
    public void testIsNullWithNotNull() {
        assertFalse(EasyMockAnnotationValidationUtils.isNull(new Object()));
    }

    @Test
    public void testIsNullWithNull() {
        assertTrue(EasyMockAnnotationValidationUtils.isNull(null));
    }

    @Test
    public void testNotNullWithNotNull() {
        assertTrue(EasyMockAnnotationValidationUtils.notNull(new Object()));
    }

    @Test
    public void testNotNullWithNull() {
        assertFalse(EasyMockAnnotationValidationUtils.notNull(null));
    }

    @Test
    public void testIsEmptyWithEmptyString() {
        assertTrue(EasyMockAnnotationValidationUtils.isEmpty(""));
    }

    @Test
    public void testIsEmptyWithNull() {
        assertTrue(EasyMockAnnotationValidationUtils.isEmpty(null));
    }

    @Test
    public void testIsEmptyWithString() {
        assertFalse(EasyMockAnnotationValidationUtils.isEmpty("String"));
    }

    @Test
    public void testAssertNotNullWithNotNull() {
        EasyMockAnnotationValidationUtils.assertNotNull(new Object(), ASSERT_MESSAGE);
    }

    @Test
    public void testAssertNotNullWithNull() {
        try {
            EasyMockAnnotationValidationUtils.assertNotNull(null, ASSERT_MESSAGE);
        } catch (IllegalArgumentException ex) {
            assertEquals(ASSERT_MESSAGE, ex.getMessage());
        }
    }
}
