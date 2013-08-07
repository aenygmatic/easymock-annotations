package org.easymock.annotation.internal;

import static org.easymock.MockType.DEFAULT;
import static org.easymock.MockType.NICE;
import static org.easymock.MockType.STRICT;

import org.easymock.EasyMock;
import org.easymock.MockType;

/**
 *
 * Creates mock via EasyMock. This is equivalent to {@code  EasyMock.createMock(class)}
 *
 * @author Balazs Berkes
 */
public class StaricMockFactory implements MockFactory {

    @Override
    public <T> T createMock(Class<T> clazz, MockType type) {
        T mock;
        switch (type) {
            case NICE:
                mock = EasyMock.createNiceMock(clazz);
                break;
            case STRICT:
                mock = EasyMock.createStrictMock(clazz);
                break;
            case DEFAULT:
                mock = EasyMock.createMock(clazz);
                break;
            default:
                mock = EasyMock.createMock(clazz);
                break;
        }
        return mock;
    }
}
