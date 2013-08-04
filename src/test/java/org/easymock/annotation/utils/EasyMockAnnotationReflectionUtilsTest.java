package org.easymock.annotation.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for {@link EasyMockAnnotationReflectionUtils}.
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotationReflectionUtilsTest {

    @Test
    public void getInheritanceDistanceShouldReturnZeroWhenClassIsNotSuperclass() {
        //GIVEN
        Object target = new Clazz();
        Class<?> classOfTarget = Clazz.class;
        //WHEN
        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, classOfTarget);
        //THEN
        assertEquals(0, inheritanceDistance);
    }

    @Test
    public void getInheritanceDistanceShouldReturnDistanceWhenClassSuperclass() {
        //GIVEN
        Object target = new SubClass();
        Class<?> classOfTarget = SuperClass.class;
        //WHEN
        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, classOfTarget);
        //THEN
        assertEquals(2, inheritanceDistance);
    }

    @Test
    public void getInheritanceDistanceShouldReturnDistanceToObjectWhenTargetIsNotRelated() {
        //GIVEN
        Object target = new SubClass();
        Class<?> classOfTarget = StringBuilder.class;
        //WHEN
        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, classOfTarget);
        //THEN
        assertEquals(-1, inheritanceDistance);
    }
}

class SuperClass {
}

class Clazz extends SuperClass {
}

class SubClass extends Clazz {
}
