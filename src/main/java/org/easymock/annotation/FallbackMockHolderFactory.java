package org.easymock.annotation;

import static org.easymock.annotation.utils.EasyMockAnnotationValidationUtils.isEmpty;
import static org.easymock.annotation.utils.EasyMockAnnotationValidationUtils.isNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.easymock.MockType;

import org.easymock.annotation.internal.ControlledMockFactory;
import org.easymock.annotation.internal.EasyMockSupportMockFactory;
import org.easymock.annotation.internal.MockFactory;
import org.easymock.annotation.internal.MockHolder;
import org.easymock.annotation.internal.StaticMockFactory;

/**
 * Creates mock by according to the given rules.
 * <p>
 * @author Balazs Berkes
 */
public class FallbackMockHolderFactory {

    private List<FallbackFactory> factories = new LinkedList<FallbackFactory>();

    public FallbackMockHolderFactory(NavigableMap<String, IMocksControl> namedContols, Object testclass) {
        if (!namedContols.isEmpty()) {
            factories.add(new NamedControlledFactory(namedContols));
            factories.add(new DefaultControlledFactory(namedContols.firstEntry().getValue()));
        }
        factories.add(new NonControlledFactory(testclass));
    }

    public MockHolder createMock(Field field, String name, MockType mockType, String contolName) {
        Object mock = createMockByFallback(field, name, mockType, contolName);
        return createMockHolder(mock, field, name);
    }

    private Object createMockByFallback(Field field, String name, MockType mockType, String contolName) {
        Object mock = null;
        for (FallbackFactory f : factories) {
            mock = f.create(field.getType(), name, mockType, contolName);
            if (mock != null) {
                break;
            }
        }
        return mock;
    }

    private MockHolder createMockHolder(Object mockedObject, Field field, String name) {
        MockHolder mock = new MockHolder();
        mock.setMock(mockedObject);
        mock.setSourceField(field);
        mock.setName(name);
        return mock;
    }

    private abstract class FallbackFactory {

        public Object create(Class<?> clazz, String name, MockType mockType, String contolName) {
            if (isEmpty(name)) {
                return createWithoutName(clazz, mockType, contolName);
            } else {
                return createWithName(clazz, mockType, name, contolName);
            }
        }

        abstract Object createWithoutName(Class<?> clazz, MockType mockType, String contolName);

        abstract Object createWithName(Class<?> clazz, MockType mockType, String name, String contolName);

    }

    private class DefaultControlledFactory extends FallbackFactory {

        private MockFactory factory;

        public DefaultControlledFactory(IMocksControl control) {
            factory = new ControlledMockFactory(control);
        }

        @Override
        Object createWithoutName(Class<?> clazz, MockType mockType, String contolName) {
            return factory.createMock(clazz, mockType);
        }

        @Override
        Object createWithName(Class<?> clazz, MockType mockType, String name, String contolName) {
            return factory.createMock(clazz, mockType, name);
        }
    }

    private class NamedControlledFactory extends FallbackFactory {

        private Map<String, MockFactory> factories = new HashMap<String, MockFactory>();

        public NamedControlledFactory(Map<String, IMocksControl> namedContols) {
            for (Map.Entry<String, IMocksControl> entry : namedContols.entrySet()) {
                factories.put(entry.getKey(), new ControlledMockFactory(entry.getValue()));
            }
        }

        @Override
        Object createWithoutName(Class<?> clazz, MockType mockType, String contolName) {
            MockFactory factory = factories.get(contolName);
            return isNull(factory) ? null : factory.createMock(clazz, mockType);
        }

        @Override
        Object createWithName(Class<?> clazz, MockType mockType, String name, String contolName) {
            MockFactory factory = factories.get(contolName);
            return isNull(factory) ? null : factory.createMock(clazz, mockType, name);
        }
    }

    private class NonControlledFactory extends FallbackFactory {

        private MockFactory factory;

        public NonControlledFactory(Object testclass) {
            if (testclass instanceof EasyMockSupport) {
                factory = new EasyMockSupportMockFactory((EasyMockSupport) testclass);
            } else {
                factory = new StaticMockFactory();
            }
        }

        @Override
        Object createWithoutName(Class<?> clazz, MockType mockType, String contolName) {
            return factory.createMock(clazz, mockType);
        }

        @Override
        Object createWithName(Class<?> clazz, MockType mockType, String name, String contolName) {
            return factory.createMock(clazz, mockType, name);
        }
    }
}
