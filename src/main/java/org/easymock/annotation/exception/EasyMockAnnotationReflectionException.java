package org.easymock.annotation.exception;

/**
 * Exception wraps exceptions thrown during reflection based operations.
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotationReflectionException extends RuntimeException {

    public EasyMockAnnotationReflectionException() {
    }

    public EasyMockAnnotationReflectionException(String message) {
        super(message);
    }

    public EasyMockAnnotationReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyMockAnnotationReflectionException(Throwable cause) {
        super(cause);
    }
}
