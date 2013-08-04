package org.easymock.annotation.internal;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link MockInjector}.
 *
 * @author Balazs Berkes
 */
public class MockInjectorTest {

    /* Fields to inject */
    private SuperClass superClass;
    private SuperClass anotherSuperClass;
    private Clazz clazz;
    private SubClass subClass;
    private SubSubClass subSubClass;
    private Set<MockHolder> mocks;
    /* Object to be injected */
    private TestedClassWithAllUniqueField classUniqueTypeField;
    private TestedClassWithFieldsOfSameType classUniqueNamedFields;
    private TestedClassWithFieldsOfSameTypeLowCaseOnly classUniqueLowCaseNamedFields;
    private MockInjector underTest;

    @Before
    public void setUp() {
        underTest = new MockInjector();
        superClass = new SuperClass();
        anotherSuperClass = new SuperClass();
        clazz = new Clazz();
        subClass = new SubClass();
        subSubClass = new SubSubClass();
    }

    @Test
    public void testInjectMocksWhenAllFieldHasUniqueType() {
        //GIVEN
        givenMocks(superClass, clazz, subClass, subSubClass);
        givenClassWithUniqueTypeFields();
        //WHEN
        underTest.injectTo(classUniqueTypeField);
        //THEN
        assertFieldsAreInjectedByType();
    }

    @Test
    public void testInjectMocksWhenFieldsHaveTheSameTypeShouldMatchByName() {
        //GIVEN
        givenMocks(superClass, anotherSuperClass);
        givenClassWithUniqueNamedFieldsOfSameType();
        //WHEN
        underTest.injectTo(classUniqueNamedFields);
        //THEN
        assertFieldsInjectedByName();
    }

    @Test
    public void testInjectMocksWhenFieldsHaveTheSameTypeShouldMatchByLowerCaseName() {
        //GIVEN
        givenMocks(superClass, anotherSuperClass);
        givenClassWithUniqueLowerCaseNamedFieldsOfSameType();
        //WHEN
        underTest.injectTo(classUniqueLowCaseNamedFields);
        //THEN
        assertFieldsInjectedByLowerCaseName();
    }

    private void givenMocks(Object... mocks) {
        this.mocks = new HashSet<MockHolder>();
        for (Object mock : mocks) {
            MockHolder m = new MockHolder();
            m.setMock(mock);
            m.setSourceField(getFieldNameOf(mock));
            this.mocks.add(m);
        }

        underTest.injectMocks(this.mocks);
    }

    private void givenClassWithUniqueTypeFields() {
        classUniqueTypeField = new TestedClassWithAllUniqueField(superClass, clazz, subClass, subSubClass);
    }

    private void assertFieldsAreInjectedByType() {
        assertEquals(superClass, classUniqueTypeField.superClass);
        assertEquals(clazz, classUniqueTypeField.clazz);
        assertEquals(subClass, classUniqueTypeField.subClass);
        assertEquals(subSubClass, classUniqueTypeField.subSubClass);
    }

    private void givenClassWithUniqueNamedFieldsOfSameType() {
        classUniqueNamedFields = new TestedClassWithFieldsOfSameType(superClass, anotherSuperClass);
    }

    private void givenClassWithUniqueLowerCaseNamedFieldsOfSameType() {
        classUniqueLowCaseNamedFields = new TestedClassWithFieldsOfSameTypeLowCaseOnly(superClass, anotherSuperClass);
    }

    private void assertFieldsInjectedByName() {
        assertEquals(superClass, classUniqueNamedFields.superClass);
        assertEquals(anotherSuperClass, classUniqueNamedFields.anotherSuperClass);
    }

    private void assertFieldsInjectedByLowerCaseName() {
        assertEquals(superClass, classUniqueLowCaseNamedFields.superclass);
        assertEquals(anotherSuperClass, classUniqueLowCaseNamedFields.anothersuperclass);
    }

    private Field getFieldNameOf(Object mock) {
        Field field = null;
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                if (mock == f.get(this)) {
                    field = f;
                }
            } catch (Exception ex) {
            }
        }
        return field;
    }

    class SuperClass {
    }

    class Clazz extends SuperClass {
    }

    class SubClass extends Clazz {
    }

    class SubSubClass extends SubClass {
    }

    class TestedClassWithAllUniqueField {

        public SuperClass superClass;
        public Clazz clazz;
        public SubClass subClass;
        public SubSubClass subSubClass;

        TestedClassWithAllUniqueField(SuperClass superClass, Clazz clazz, SubClass subClass, SubSubClass subSubClass) {
            this.superClass = superClass;
            this.clazz = clazz;
            this.subClass = subClass;
            this.subSubClass = subSubClass;
        }
    }

    class TestedClassWithFieldsOfSameType {

        public SuperClass superClass;
        public SuperClass anotherSuperClass;

        TestedClassWithFieldsOfSameType(SuperClass superClass, SuperClass anotherSuperClass) {
            this.superClass = superClass;
            this.anotherSuperClass = anotherSuperClass;
        }
    }

    class TestedClassWithFieldsOfSameTypeLowCaseOnly {

        public SuperClass superclass;
        public SuperClass anothersuperclass;

        TestedClassWithFieldsOfSameTypeLowCaseOnly(SuperClass superClass, SuperClass anotherSuperClass) {
            this.superclass = superClass;
            this.anothersuperclass = anotherSuperClass;
        }
    }
}
