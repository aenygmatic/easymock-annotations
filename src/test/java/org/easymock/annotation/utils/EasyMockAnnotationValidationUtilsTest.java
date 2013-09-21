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
