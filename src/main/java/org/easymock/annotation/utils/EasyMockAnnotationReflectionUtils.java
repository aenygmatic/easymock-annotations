/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.utils;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;

import org.easymock.annotation.exception.EasyMockAnnotationReflectionException;

/**
 * Untility class for reflection based operations.
 *
 * @author Balazs Berkes
 */
public final class EasyMockAnnotationReflectionUtils {

    /**
     * Set the field of the target object with the given value.
     *
     * @param field field to set
     * @param target object with field to set
     * @param value value which will be used for the field
     *
     * @throws throws {@code EasyMockAnnotationReflectionException} when setting the
     * field failed
     */
    public static void setField(Field field, Object target, Object value) {
        if (!isStatic(field.getModifiers()) && !isFinal(field.getModifiers())) {
            field.setAccessible(true);
            try {
                field.set(target, value);
            } catch (IllegalArgumentException ex) {
                throw new EasyMockAnnotationReflectionException("Cannot set field", ex);
            } catch (IllegalAccessException ex) {
                throw new EasyMockAnnotationReflectionException("Cannot set field", ex);
            }
        }
    }

    public static Object getField(Field field, Object target) {
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

    private EasyMockAnnotationReflectionUtils() {
    }
}
