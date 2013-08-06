/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

import integrationtest.support.ComponentWithGenericFields;

/**
 *
 * @author Balazs Berkes
 */
public class GenericCollectionInitializationIntegrationTest {

    @Mock
    private Set<String> strings;
    @Mock
    private List<String> list;
    @Mock
    private Map<String, Object> map;
    @Injected
    private ComponentWithGenericFields underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @Test
    public void testInitializeShouldCreateMockAndInjectThem() {
        //GIVEN annotated fields in this class
        //WHEN  setUp method is called
        //AND   this method is called
        //THEN
        assertNotNull(underTest.getList());
        assertNotNull(underTest.getMap());
        assertNotNull(underTest.getSet());
    }

    @Test
    public void testInitializeShouldCreateMockAndWriteBackToTestClassFields() {
        //GIVEN annotated fields in this class
        //WHEN  setUp method is called
        //AND   this method is called
        //THEN
        assertNotNull(strings);
        assertNotNull(list);
        assertNotNull(map);
    }

    @Test
    public void testInitializeShouldInjectSameMockToTestClassAndUnderTest() {
        //GIVEN annotated fields in this class
        //WHEN  setUp method is called
        //AND   this method is called
        //THEN
        assertEquals(strings, underTest.getSet());
        assertEquals(list, underTest.getList());
        assertEquals(map, underTest.getMap());
    }

    @Test
    public void testInitializeShouldInejctMocksByGenericType() {
        //GIVEN annotated fields in this class
        //WHEN  setUp method is called
        //AND   this method is called
        //THEN
        assertEquals(strings, underTest.getSet());
        assertEquals(list, underTest.getList());
        assertEquals(map, underTest.getMap());
    }

    @Test
    @Ignore("Not implemented yet")
    public void testInitializeShouldInejctMocksByGenericParameterWhenTheyHaveTheSameType() {
    }
}
