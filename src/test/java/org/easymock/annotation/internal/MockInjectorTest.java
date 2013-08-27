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
    private HashSet<String> set;
    /* Objects to be injected */
    private TestedClassWithAllUniqueField classUniqueTypeField;
    private TestedClassWithFieldsOfSameType classUniqueNamedFields;
    private TestedClassWithFieldsOfSameTypeLowCaseOnly classUniqueLowCaseNamedFields;
    private TestedClassWithInterfaceField classWithInterfaceField;
    private TestedClassWithInheritedFields classWithInheritedFields;

    private Set<MockHolder> mocks;

    private MockInjector underTest;

    @Before
    public void setUp() {
        initializeTestComponents();
        underTest = new MockInjector();
    }

    @Test
    public void testInjectMocksWhenAllFieldHasUniqueType() {
        givenMocks(superClass, clazz, subClass);
        givenClassWithUniqueTypeFields();

        underTest.injectTo(classUniqueTypeField);

        assertFieldsAreInjectedByType();
    }

    @Test
    public void testInjectMocksWhenFieldsHaveTheSameTypeShouldMatchByName() {
        givenMocks(superClass, anotherSuperClass);
        givenClassWithUniqueNamedFieldsOfSameType();

        underTest.injectTo(classUniqueNamedFields);

        assertFieldsInjectedByName();
    }

    @Test
    public void testInjectMocksWhenFieldsHaveTheSameTypeShouldMatchByLowerCaseName() {
        givenMocks(superClass, anotherSuperClass);
        givenClassWithUniqueLowerCaseNamedFieldsOfSameType();

        underTest.injectTo(classUniqueLowCaseNamedFields);

        assertFieldsInjectedByLowerCaseName();
    }

    @Test
    public void testInjectMocksWhenFieldIsInterfaceShouldInjectImplementation() {
        givenMocks(set);
        givenTestedClassWithInterfaceField();

        underTest.injectTo(classWithInterfaceField);

        assertInterfaceImplIsInjected();

    }

    @Test
    public void testInjectMocksShouldInjectInheritedFields() {
        givenMocks(superClass, clazz, subClass);
        givenTestedClassWithInherite4dFields();

        underTest.injectTo(classWithInheritedFields);

        assertInheritedFieldsAreInjected();

    }

    private void givenMocks(Object... mocks) {
        this.mocks = new HashSet<MockHolder>();
        for (Object mock : mocks) {
            MockHolder m = new MockHolder();
            m.setMock(mock);
            m.setSourceField(getFieldNameOf(mock));
            this.mocks.add(m);
        }
        underTest.addMocks(this.mocks);
    }

    private void givenTestedClassWithInterfaceField() {
        classWithInterfaceField = new TestedClassWithInterfaceField();
    }

    private void givenTestedClassWithInherite4dFields() {
        classWithInheritedFields = new TestedClassWithInheritedFields();
    }

    private void assertInheritedFieldsAreInjected() {
        assertEquals(superClass, classWithInheritedFields.superClass);
        assertEquals(clazz, classWithInheritedFields.clazz);
        assertEquals(subClass, classWithInheritedFields.subClass);
    }

    private void assertFieldsAreInjectedByType() {
        assertEquals(superClass, classUniqueTypeField.superClass);
        assertEquals(clazz, classUniqueTypeField.clazz);
        assertEquals(subClass, classUniqueTypeField.subClass);
    }

    private void givenClassWithUniqueTypeFields() {
        classUniqueTypeField = new TestedClassWithAllUniqueField();
    }

    private void givenClassWithUniqueNamedFieldsOfSameType() {
        classUniqueNamedFields = new TestedClassWithFieldsOfSameType();
    }

    private void givenClassWithUniqueLowerCaseNamedFieldsOfSameType() {
        classUniqueLowCaseNamedFields = new TestedClassWithFieldsOfSameTypeLowCaseOnly();
    }

    private void assertInterfaceImplIsInjected() {
        assertEquals(set, classWithInterfaceField.strings);
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

    private void initializeTestComponents() {
        superClass = new SuperClass();
        anotherSuperClass = new SuperClass();
        clazz = new Clazz();
        subClass = new SubClass();
        set = new HashSet<String>();
    }

    class SuperClass {
    }

    class Clazz extends SuperClass {
    }

    class SubClass extends Clazz {
    }

    class TestedClassWithAllUniqueField {

        public SuperClass superClass;
        public Clazz clazz;
        public SubClass subClass;
    }

    class TestedClassWithInheritedFields extends TestedClassWithAllUniqueField {
    }

    class TestedClassWithFieldsOfSameType {

        public SuperClass superClass;
        public SuperClass anotherSuperClass;
    }

    class TestedClassWithFieldsOfSameTypeLowCaseOnly {

        public SuperClass superclass;
        public SuperClass anothersuperclass;
    }

    class TestedClassWithInterfaceField {

        public Set<String> strings;
    }
}
