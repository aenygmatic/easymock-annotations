package org.easymock.annotation.internal;

/**
 * Interface which provides an API for creating mocks.
 *
 * @author Balazs Berkes
 */
public interface MockFactory {

    /**
     * Created a mock object of the given class.
     *
     * @param clazz type of the mock
     * @return returns a mocked object.
     */
    public Object createMock(Class<?> clazz);
}
