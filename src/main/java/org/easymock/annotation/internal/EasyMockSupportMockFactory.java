/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.internal;

import static org.easymock.MockType.DEFAULT;
import static org.easymock.MockType.NICE;
import static org.easymock.MockType.STRICT;

import org.easymock.EasyMockSupport;
import org.easymock.MockType;

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
    public Object createMock(Class<?> clazz, MockType type) {
        Object mock = null;
        switch (type) {
            case NICE:
                mock = easyMockSupport.createNiceControl();
                break;
            case STRICT:
                mock = easyMockSupport.createStrictControl();
                break;
            case DEFAULT:
                mock = easyMockSupport.createControl();
                break;
        }
        return mock;
    }
}
