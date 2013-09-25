package org.easymock.annotation.internal;

import static org.easymock.annotation.utils.EasyMockAnnotationValidationUtils.isNull;
import static org.easymock.annotation.utils.EasyMockAnnotationValidationUtils.notNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.easymock.annotation.exception.EasyMockAnnotationInitializationException;
import org.easymock.annotation.internal.selection.ByTypeSelector;
import org.easymock.annotation.internal.selection.MockSelector;

/**
 * Creates a new instance of the given class.
 *
 * @author Balazs Berkes
 */
public class ClassInitializer {

    /**
     * Initialize an instance of the given class. If the class has multiple constructors the consturctor with the less
     * parameter will be prefered, however if the initialization fails more parametrized constructor will be the next.
     * <p>
     * @param clazz class to be initialized
     * @param mocks list of mocked object which can be used as constructor parameter
     * @return a new instance of the given class.
     * <p>
     * @throws EasyMockAnnotationInitializationException when initialization failed
     */
    public Object initialize(Class<?> clazz, List<MockHolder> mocks) throws EasyMockAnnotationInitializationException {
        return new Initializer(clazz).withParameters(mocks).initialize();
    }

    private static class Initializer {

        private static final ConstructorComparator CONSTRUCTOR_COMPARATOR = new ConstructorComparator();
        private static final MockSelector<Class<?>> byTypeSelector = ByTypeSelector.getSingleton();

        private List<MockHolder> mocks = Collections.emptyList();
        private List<Constructor<?>> constructors;

        private Initializer(Class<?> clazz) {
            constructors = Arrays.asList(clazz.getDeclaredConstructors());
            Collections.sort(constructors, CONSTRUCTOR_COMPARATOR);
        }

        private Initializer withParameters(List<MockHolder> parameters) {
            mocks = parameters;
            return this;
        }

        private Object initialize() throws EasyMockAnnotationInitializationException {
            Object testedObject = initializeWithDefaultConstructor();
            if (isNull(testedObject)) {
                testedObject = initializeWithParameters();
            }
            if (isNull(testedObject)) {
                throwEasyMockAnnotationInitializationException();
            }
            return testedObject;
        }

        private Object initializeWithDefaultConstructor() {
            Constructor<?> defaultConstructor = findDefaultConstructor();
            return exceptionFreeNewInstance(defaultConstructor);
        }

        private Constructor<?> findDefaultConstructor() throws EasyMockAnnotationInitializationException, SecurityException {
            Constructor<?> defaultConstructor = null;
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterTypes().length == 0) {
                    defaultConstructor = constructor;
                    break;
                }
            }
            return defaultConstructor;
        }

        private Object initializeWithParameters() {
            Object testedObject = null;
            for (Constructor<?> constructor : constructors) {
                constructor.setAccessible(true);
                testedObject = tryToCreateInstance(constructor);
                if (notNull(testedObject)) {
                    break;
                }
            }
            return testedObject;
        }

        private Object tryToCreateInstance(Constructor<?> constructor) {
            Object instance = null;
            List<Object> parameterCandidates = selectParameterCandidates(constructor);
            if (!parameterCandidates.isEmpty()) {
                instance = exceptionFreeNewInstance(constructor, parameterCandidates.toArray());
            }
            return instance;
        }

        private List<Object> selectParameterCandidates(Constructor<?> constructor) {
            List<Object> parameterMocks = new LinkedList<Object>();
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                List<MockHolder> matchingMocks = byTypeSelector.getMatchingMocks(parameterType, mocks);
                if (matchingMocks.isEmpty()) {
                    break;
                }
                parameterMocks.add(matchingMocks.get(0).getMock());
            }
            return parameterMocks;
        }

        private Object exceptionFreeNewInstance(Constructor<?> constructor, Object... arguments) {
            Object newInstance = null;
            try {
                newInstance = constructor.newInstance(arguments);
            } catch (Exception ignored) {
                /* Ignoring any exception */
            }
            return newInstance;
        }

        private void throwEasyMockAnnotationInitializationException() throws EasyMockAnnotationInitializationException {
            throw new EasyMockAnnotationInitializationException("I tried to create an instance of @Injected class"
                    + " but it failed. Please provide an instance of the class before initializing the annotations.");
        }
    }

    private static class ConstructorComparator implements Comparator<Constructor<?>> {

        public int compare(Constructor<?> left, Constructor<?> right) {
            return left.getParameterTypes().length - right.getParameterTypes().length;
        }
    }
}
