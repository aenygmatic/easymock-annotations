/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.internal;

import org.easymock.EasyMockSupport;

/**
 *
 * Creates mock via the given class which inherits from {@link EasyMockSupport}.
 *
 * @author Balazs Berkes
 */
public class EasyMockSupportMockFactory implements MockFactory {

    private EasyMockSupport easyMockSupport;

    public EasyMockSupportMockFactory(EasyMockSupport easyMockSupport) {
        this.easyMockSupport = easyMockSupport;
    }

    @Override
    public Object createMock(Class<?> clazz) {
        return easyMockSupport.createMock(clazz);
    }
}
