/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.internal;

import org.easymock.IMocksControl;
import org.easymock.MockType;

/**
 *
 * Creates mock via the given {@link IMocksControl}.
 *
 * @author Balazs Berkes
 */
public class ControlledMockFactory implements MockFactory {

    private IMocksControl control;

    public ControlledMockFactory(IMocksControl control) {
        this.control = control;
    }

    /**
     * Created a mock object of the given class.
     *
     * @param clazz type of the mock
     * @param type {@link MockType} will be ignored
     * @return returns a mocked object.
     */
    @Override
    public <T> T createMock(Class<T> clazz, MockType type) {
        return control.createMock(clazz);
    }
}
