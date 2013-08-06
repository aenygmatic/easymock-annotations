/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

import integrationtest.support.FacadeWithNonRelatedComponents;
import integrationtest.support.IndependentObject;
import integrationtest.support.ThridLevelClassA;

/**
 *
 * @author Balazs Berkes
 */
public class EasyMockAnnotationStaticInitializeTest {

    @Mock
    private ThridLevelClassA component1;
    @Mock
    private IndependentObject component2;
    @Injected
    private FacadeWithNonRelatedComponents underTest;

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
        assertNotNull(underTest.getThirdLevelClassA());
        assertNotNull(underTest.getIndependentObject());
    }

    @Test
    public void testInitializeShouldCreateMockAndWriteBackToTestClassFields() {
        //GIVEN annotated fields in this class
        //WHEN  setUp method is called
        //AND   this method is called
        //THEN
        assertNotNull(component1);
        assertNotNull(component2);
    }

    @Test
    public void testInitializeShouldInjectSameMockToTestClassAndUnderTest() {
        //GIVEN annotated fields in this class
        //WHEN  setUp method is called
        //AND   this method is called
        //THEN
        assertEquals(component1, underTest.getThirdLevelClassA());
        assertEquals(component2, underTest.getIndependentObject());
    }
}
