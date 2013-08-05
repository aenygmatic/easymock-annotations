package org.easymock.annotation.internal;

import org.easymock.MockType;

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
     * @param type {@link MockType} of the mock
     * @return returns a mocked object.
     */
    Object createMock(Class<?> clazz, MockType type);
}
