package org.easymock.annotation.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.easymock.annotation.exception.EasyMockAnnotationInitializationException;

/**
 * Creates a new instance of the given class.
 *
 * @author Balazs Berkes
 */
public class ClassInitializer {

    private Map<Class<?>, Object> parameters;

    /**
     * Initialize an instance of the given class if it has default construtor.
     * <p>
     * @param clazz class to be initialized
     * @return a new instancce of the given class.
     * <p>
     * @throws EasyMockAnnotationInitializationException when initailzation failed or no default constructor
     * found.
     */
    public Object initialize(Class<?> clazz) throws EasyMockAnnotationInitializationException {
        Object testedObject = null;
        try {
            Constructor<?>[] ctors = clazz.getDeclaredConstructors();
            Constructor<?> constructor = findDefaultConstructor(ctors);
            testedObject = constructor.newInstance();
        } catch (InstantiationException ex) {
            throwEasyMockAnnotationInitializationException(ex);
        } catch (IllegalAccessException ex) {
            throwEasyMockAnnotationInitializationException(ex);
        } catch (IllegalArgumentException ex) {
            throwEasyMockAnnotationInitializationException(ex);
        } catch (InvocationTargetException ex) {
            throwEasyMockAnnotationInitializationException(ex);
        }
        return testedObject;
    }

    /* "Not implemented yet" */
    @Deprecated
    public ClassInitializer addConstructorParam(Object param) {
        if (parameters == null) {
            parameters = new HashMap<Class<?>, Object>();
        }
        parameters.put(param.getClass(), param);
        return this;
    }

    private Constructor<?> findDefaultConstructor(Constructor[] constructors) {
        Constructor<?> ctor = null;
        for (Constructor<?> c : constructors) {
            if (c.getGenericParameterTypes().length == 0) {
                ctor = c;
                break;
            }
        }
        if (ctor == null) {
            throw new EasyMockAnnotationInitializationException("Not default contructor found ");
        }
        ctor.setAccessible(true);
        return ctor;
    }

    private void throwEasyMockAnnotationInitializationException(Throwable ex) throws EasyMockAnnotationInitializationException {
        throw new EasyMockAnnotationInitializationException("Failed to initialized tested class!", ex);
    }
}
