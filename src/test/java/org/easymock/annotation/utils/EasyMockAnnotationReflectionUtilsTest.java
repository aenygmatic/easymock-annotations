package org.easymock.annotation.utils;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

/**
 * Unit test for {@link EasyMockAnnotationReflectionUtils}.
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotationReflectionUtilsTest {

    private Field field;
    private Object target;
    private Class<?> clazz;
    public Map<Integer, String> fieldWithGenericParams;

    @After
    public void tearDown() {
        cleanUpFields();
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnZeroWhenClassIsNotSuperclass() {
        givenInstanceAndClassOf(new Clazz(), Clazz.class);

        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, clazz);

        assertEquals(0, inheritanceDistance);
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnDistanceWhenClassSuperclass() {
        givenInstanceAndClassOf(new SubClass(), SuperClass.class);

        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, clazz);

        assertEquals(2, inheritanceDistance);
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnDistanceToObjectWhenTargetIsNotRelated() {
        givenInstanceAndClassOf(new SubClass(), StringBuilder.class);

        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(target, clazz);

        assertEquals(-1, inheritanceDistance);
    }

    @Test
    public void testGetAllFieldShouldReturnAllFieldsUpToObject() {
        givenClassOf(SubClass.class);

        List<Field> allFields = EasyMockAnnotationReflectionUtils.getAllFields(SubClass.class);

        assertEquals(3, allFields.size());
    }

    @Test
    public void testGetGenericParametersShouldReturnGenericParametersOfTheField() throws Exception {
        givenField("fieldWithGenericParams");

        List<Type> genericParameters = EasyMockAnnotationReflectionUtils.getGenericParameters(field);

        assertEquals(Integer.class, genericParameters.get(0));
        assertEquals(String.class, genericParameters.get(1));
    }

    private void cleanUpFields() {
        target = null;
        clazz = null;
        field = null;
    }

    private void givenInstanceAndClassOf(Object target, Class<?> clazz) {
        this.target = target;
        this.clazz = clazz;
    }

    private void givenClassOf(Class<?> clazz) {
        this.clazz = clazz;
    }

    private void givenField(String name) throws Exception {
        this.field = this.getClass().getField(name);
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
