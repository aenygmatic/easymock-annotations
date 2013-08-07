package org.easymock.annotation.utils;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for {@link EasyMockAnnotationReflectionUtils}.
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotationReflectionUtilsTest {

    @Test
    public void testGetInheritanceDistanceShouldReturnZeroWhenClassIsNotSuperclass() {
        //GIVEN
        Object target = new Clazz();
        Class<?> classOfTarget = Clazz.class;
        //WHEN
        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, classOfTarget);
        //THEN
        assertEquals(0, inheritanceDistance);
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnDistanceWhenClassSuperclass() {
        //GIVEN
        Object target = new SubClass();
        Class<?> clazz = SuperClass.class;
        //WHEN
        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, clazz);
        //THEN
        assertEquals(2, inheritanceDistance);
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnDistanceToObjectWhenTargetIsNotRelated() {
        //GIVEN
        Object target = new SubClass();
        Class<?> clazz = StringBuilder.class;
        //WHEN
        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, clazz);
        //THEN
        assertEquals(-1, inheritanceDistance);
    }

    @Test
    public void testGetAllFieldShouldReturnAllFieldsUpToObject() {
        //GIVEN Class with predecessors with fields.
        //WHEN
        List<Field> allFields = EasyMockAnnotationReflectionUtils.getAllFields(SubClass.class);
        //THEN
        assertEquals(3, allFields.size());
    }
}

class SuperClass {

    String superClassField;
}

class Clazz extends SuperClass {

    String classField;
}

class SubClass extends Clazz {

    String subClassField;
}
