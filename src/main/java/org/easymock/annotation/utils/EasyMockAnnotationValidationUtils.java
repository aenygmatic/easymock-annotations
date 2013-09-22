package org.easymock.annotation.utils;

/**
 * Utility class for validation based operations.
 * <p>
 * @author Balazs Berkes
 */
public final class EasyMockAnnotationValidationUtils {

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean notNull(Object object) {
        return object != null;
    }

    public static void assertNotNull(Object object, String message) throws IllegalArgumentException {
        if (isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    private EasyMockAnnotationValidationUtils(){
    }
}
