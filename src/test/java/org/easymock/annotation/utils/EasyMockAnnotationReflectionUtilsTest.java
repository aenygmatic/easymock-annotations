/* 
 * Copyright 2013 Balazs Berkes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.easymock.annotation.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import org.easymock.annotation.utils.EasyMockAnnotationReflectionUtils.UnableToWriteFieldException;

/**
 * Unit test for {@link EasyMockAnnotationReflectionUtils}.
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotationReflectionUtilsTest {

    private Field field;
    private Object instance;
    private Class<?> clazz;
    private StringBuilder stringBuilder;
    private Map<Integer, String> fieldWithGenericParams;
    private final Object finalInstance = new Object();
    private static Object staticInstance = new Object();

    @After
    public void tearDown() {
        cleanUpFields();
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnZeroWhenClassIsNotSuperclass() {
        givenInstanceAndClass(new Clazz(), Clazz.class);

        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(instance, clazz);

        assertEquals(0, inheritanceDistance);
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnDistanceWhenClassSuperclass() {
        givenInstanceAndClass(new SubClass(), SuperClass.class);

        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(instance, clazz);

        assertEquals(2, inheritanceDistance);
    }

    @Test
    public void testGetInheritanceDistanceShouldReturnDistanceToObjectWhenTargetIsNotRelated() {
        givenInstanceAndClass(new SubClass(), StringBuilder.class);

        int inheritanceDistance = EasyMockAnnotationReflectionUtils.getInheritanceDistance(instance, clazz);

        assertEquals(-1, inheritanceDistance);
    }

    @Test
    public void testGetAllDeclaredFieldShouldReturnAllFieldsUpToObject() {
        givenClassOf(SubClass.class);

        List<Field> allFields = EasyMockAnnotationReflectionUtils.getAllDeclaredFields(clazz);

        assertEquals(3, allFields.size());
    }

    @Test
    public void testGetGenericParametersShouldReturnGenericParametersOfTheField() throws Exception {
        givenField("fieldWithGenericParams");

        List<Type> genericParameters = EasyMockAnnotationReflectionUtils.getGenericParameters(field);

        assertEquals(Integer.class, genericParameters.get(0));
        assertEquals(String.class, genericParameters.get(1));
    }

    @Test
    public void testSetFieldShouldSetField() throws Exception {
        givenField("instance");
        Object value = new Object();

        EasyMockAnnotationReflectionUtils.setField(field, this, value);

        assertEquals(value, field.get(this));
    }

    @Test
    public void testSetFieldShouldNotSetFieldWhenValueIsNull() throws Exception {
        givenField("instance");
        instance = new Object();

        EasyMockAnnotationReflectionUtils.setField(field, this, null);

        assertNotNull(instance);
    }

    @Test
    public void testSetFieldShouldNotSetFieldWhenItsFinal() throws Exception {
        givenField("finalInstance");
        instance = new Object();

        EasyMockAnnotationReflectionUtils.setField(field, this, instance);

        assertNotEquals(instance, field.get(this));
    }

    @Test
    public void testSetFieldShouldNotSetFieldWhenItsStatic() throws Exception {
        givenField("staticInstance");
        instance = new Object();

        EasyMockAnnotationReflectionUtils.setField(field, this, instance);

        assertNotEquals(instance, field.get(this));
    }

    @Test(expected = UnableToWriteFieldException.class)
    public void testSetFieldShouldThrowExceptionWhenCannotSet() throws Exception {
        givenField("stringBuilder");

        EasyMockAnnotationReflectionUtils.setField(field, this, new Object());
    }

    @Test
    public void testGetFieldShouldGetFieldValue() throws Exception {
        givenField("stringBuilder");
        stringBuilder = new StringBuilder();

        Object fieldValue = EasyMockAnnotationReflectionUtils.getField(field, this);

        assertEquals(stringBuilder, fieldValue);
    }

    @Test
    public void testGetFieldShouldReturnNullWhenTargetObjectIsNull() throws Exception {
        assertNull(EasyMockAnnotationReflectionUtils.getField(field, null));
    }

    private void cleanUpFields() {
        instance = null;
        clazz = null;
        field = null;
        stringBuilder = null;
    }

    private void givenInstanceAndClass(Object instance, Class<?> clazz) {
        this.instance = instance;
        this.clazz = clazz;
    }

    private void givenClassOf(Class<?> clazz) {
        this.clazz = clazz;
    }

    private void givenField(String name) throws Exception {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                this.field = field;
                break;
            }
        }
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
