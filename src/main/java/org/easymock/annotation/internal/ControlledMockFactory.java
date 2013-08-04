/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.internal;

import org.easymock.IMocksControl;

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

    @Override
    public Object createMock(Class<?> clazz) {
        return control.createMock(clazz);
    }
}
