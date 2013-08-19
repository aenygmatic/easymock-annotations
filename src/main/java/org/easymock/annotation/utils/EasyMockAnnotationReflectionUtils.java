/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.utils;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.easymock.annotation.exception.EasyMockAnnotationReflectionException;

/**
 * Untility class for reflection based operations.
 *
 * @author Balazs Berkes
 */
public final class EasyMockAnnotationReflectionUtils {

    /**
     * Set the field of the target object with the given value.
     * <p>
     * @param field field to set
     * @param target object with field to set
     * @param value value which will be used for the field
     * <p>
     * @throws EasyMockAnnotationReflectionException when setting the
     * field failed
     */
    public static void setField(Field field, Object target, Object value) throws EasyMockAnnotationReflectionException {
        if (value != null) {
            if (!isStatic(field.getModifiers()) && !isFinal(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    field.set(target, value);
                } catch (IllegalArgumentException ex) {
                    throw new EasyMockAnnotationReflectionException(String.format("Cannot set field '%s' in %s", field.getName(), target.getClass()), ex);
                } catch (IllegalAccessException ex) {
                    throw new EasyMockAnnotationReflectionException(String.format("Cannot set field '%s' in %s", field.getName(), target.getClass()), ex);
                }
            }
        }
    }

    /**
     * Get the value of the field in the given object.
     * <p>
     * @param field whiches value will be returned
     * @param target the target object which contains the field
     * @return the value of the field
     * <p>
     * @throws EasyMockAnnotationReflectionException when setting the
     * field failed
     */
    public static Object getField(Field field, Object target) throws EasyMockAnnotationReflectionException {
        Object fieldValue = null;
        if (!isStatic(field.getModifiers()) && !isFinal(field.getModifiers())) {
            field.setAccessible(true);
            try {
                fieldValue = field.get(target);
            } catch (IllegalArgumentException ex) {
                throw new EasyMockAnnotationReflectionException("Cannot get field", ex);
            } catch (IllegalAccessException ex) {
                throw new EasyMockAnnotationReflectionException("Cannot get field", ex);
            }
        }
        return fieldValue;
    }

    /**
     * Determines the distance of the given object to the given class in the
     * inheritance tree.
     *
     * @param object the root object
     * @param clazz the class to deterime the distance to
     *
     * @return the distance of the object to the class. If the object is not an
     * instance of the class {@code -1} will return.
     */
    public static int getInheritanceDistance(Object object, Class<?> clazz) {
        int distance = 0;
        Class<?> compared = object.getClass();
        boolean instanceOfClass = clazz.isInstance(object);

        if (!instanceOfClass) {
            distance = -1;
        } else {
            while (compared != clazz) {
                compared = compared.getSuperclass();
                distance++;
                if (compared == Object.class) {
                    break;
                }
            }
        }
        return distance;
    }

    /**
     * Scans for the class and all its predecessors (up to {@code Object}) for fields.
     * <p>
     * @param clazz first level class to scan
     * @return {@code List<Field>} of the class and all it predecessors
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new LinkedList<Field>();
        Class<?> predecessor = clazz;

        while (!predecessor.equals(Object.class)) {
            fields.addAll(Arrays.asList(predecessor.getDeclaredFields()));
            predecessor = predecessor.getSuperclass();
        }

        return fields;
    }

    /**
     * Returns the generic parameters of the given field.
     * <p>
     * @param field field to be scanned
     * @return the generic parameters
     */
    public static List<Type> getGenericParameters(Field field) {
        List<Type> genericParameters = new LinkedList<Type>();
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            genericParameters.addAll(Arrays.asList(parameterizedType.getActualTypeArguments()));
        }
        return genericParameters;
    }

    private EasyMockAnnotationReflectionUtils() {
    }
}
