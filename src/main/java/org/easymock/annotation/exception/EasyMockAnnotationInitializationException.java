package org.easymock.annotation.exception;

/**
 * Exception is thrown when the initalization of a class failed.
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotationInitializationException extends RuntimeException {

    public EasyMockAnnotationInitializationException() {
    }

    public EasyMockAnnotationInitializationException(String message) {
        super(message);
    }

    public EasyMockAnnotationInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyMockAnnotationInitializationException(Throwable cause) {
        super(cause);
    }
}
