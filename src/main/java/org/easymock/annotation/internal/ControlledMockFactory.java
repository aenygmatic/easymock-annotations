package org.easymock.annotation.internal;

import org.easymock.IMocksControl;
import org.easymock.MockType;

/**
 * Creates mock via the given {@link IMocksControl}.
 *
 * @author Balazs Berkes
 */
public class ControlledMockFactory implements MockFactory {

    private final IMocksControl control;

    public ControlledMockFactory(IMocksControl control) {
        this.control = control;
    }

    /**
     * Creates a mock object of the given class.
     * <p>
     * @param clazz type of the mock
     * @param type {@link MockType} will be ignored
     * @return returns a mocked object.
     */
    @Override
    public <T> T createMock(Class<T> clazz, MockType type) {
        return control.createMock(clazz);
    }

    /**
     * Created a mock object of the given class.
     * <p>
     * @param clazz type of the mock
     * @param type {@link MockType} will be ignored
     * @param name name of the mock
     * @return returns a mocked object.
     */
    @Override
    public <T> T createMock(Class<T> clazz, MockType type, String name) {
        return control.createMock(name, clazz);
    }
}
