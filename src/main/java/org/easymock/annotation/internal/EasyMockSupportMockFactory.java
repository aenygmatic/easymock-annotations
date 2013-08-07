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

    private final EasyMockSupport easyMockSupport;

    public EasyMockSupportMockFactory(EasyMockSupport easyMockSupport) {
        this.easyMockSupport = easyMockSupport;
    }

    @Override
    public <T> T createMock(Class<T> clazz, MockType type) {
        T mock;
        switch (type) {
            case NICE:
                mock = easyMockSupport.createNiceMock(clazz);
                break;
            case STRICT:
                mock = easyMockSupport.createStrictMock(clazz);
                break;
            case DEFAULT:
                mock = easyMockSupport.createMock(clazz);
                break;
            default:
                mock = easyMockSupport.createMock(clazz);
                break;
        }
        return mock;
    }
}
