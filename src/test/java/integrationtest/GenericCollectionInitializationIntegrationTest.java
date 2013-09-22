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
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

import integrationtest.support.ComponentWithGenericFields;

/**
 * Integration test for fields annotated with {@code @Mock} and with generic parameters.
 * <p>
 * @author Balazs Berkes
 */
public class GenericCollectionInitializationIntegrationTest {

    @Mock
    private Set<Object> objects;
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
    public void testInitializeShouldCreateMockAndInjectMocks() {
        assertNotNull(underTest.getList());
        assertNotNull(underTest.getMap());
        assertNotNull(underTest.getSet());
    }

    @Test
    public void testInitializeShouldCreateMockAndWriteBackToTestClassFields() {
        assertNotNull(strings);
        assertNotNull(list);
        assertNotNull(map);
    }

    @Test
    public void testInitializeShouldInjectSameMockToTestClassAndUnderTest() {
        assertEquals(strings, underTest.getSet());
        assertEquals(list, underTest.getList());
        assertEquals(map, underTest.getMap());
    }

    @Test
    public void testInitializeShouldInejctMocksByGenericType() {
        assertEquals(strings, underTest.getSet());
        assertEquals(list, underTest.getList());
        assertEquals(map, underTest.getMap());
    }

    @Test
    public void testInitializeShouldInejctMocksByGenericParameterWhenTheyHaveTheSameType() {
        assertEquals(objects, underTest.getObjectSet());
        assertEquals(strings, underTest.getSet());
    }
}
