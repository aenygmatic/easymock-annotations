package org.easymock.annotation.internal;

import org.easymock.EasyMock;

/**
 *
 * Creates mock via EasyMock. This is equivalent to {@code  EasyMock.createMock(class)}
 *
 * @author Balazs Berkes
 */
public class StaricMockFactory implements MockFactory {

    @Override
    public Object createMock(Class<?> clazz) {
        return EasyMock.createMock(clazz);
    }
}
