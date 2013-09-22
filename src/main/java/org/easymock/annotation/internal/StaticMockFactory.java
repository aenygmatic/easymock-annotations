package org.easymock.annotation.internal;

import org.easymock.EasyMock;
import org.easymock.MockType;

/**
 * Creates mock via EasyMock. This is equivalent to {@code  EasyMock.createMock(class)}
 *
 * @author Balazs Berkes
 */
public class StaticMockFactory implements MockFactory {

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
            default:
                mock = EasyMock.createMock(clazz);
                break;
        }
        return mock;
    }

    @Override
    public <T> T createMock(Class<T> clazz, MockType type, String name) {
        T mock;
        switch (type) {
            case NICE:
                mock = EasyMock.createNiceMock(name, clazz);
                break;
            case STRICT:
                mock = EasyMock.createStrictMock(name, clazz);
                break;
            case DEFAULT:
            default:
                mock = EasyMock.createMock(name, clazz);
                break;
        }
        return mock;
    }
}
