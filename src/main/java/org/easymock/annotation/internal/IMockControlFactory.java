package org.easymock.annotation.internal;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.easymock.MockType;

/**
 * Factory for creating {@link IMocksControl}.
 * <p>
 * @author Balazs Berkes
 */
public class IMockControlFactory {

    private static IMockControlFactory SINGLETON;

    public static synchronized IMockControlFactory getSingleton() {
        if (SINGLETON == null) {
            SINGLETON = new IMockControlFactory();
        }
        return SINGLETON;
    }

    /**
     * Returns a new instance of {@link IMocksControl} according to the given {@link MockType}.
     * <p>
     * @param type type of the created {@code IMocksControl}
     * @return new instance of {@code IMocksControl}
     */
    public IMocksControl createControl(MockType type) {
        IMocksControl control;
        switch (type) {
            case NICE:
                control = EasyMock.createNiceControl();
                break;
            case STRICT:
                control = EasyMock.createStrictControl();
                break;
            case DEFAULT:
            default:
                control = EasyMock.createControl();
        }
        return control;
    }

    IMockControlFactory() {
    }
}
