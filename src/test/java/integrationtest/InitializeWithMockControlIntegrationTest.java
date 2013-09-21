package integrationtest;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;
import org.easymock.annotation.MockControl;

import integrationtest.support.FacadeWithNonRelatedComponents;
import integrationtest.support.IndependentObject;
import integrationtest.support.ThridLevelClassA;

/**
 * Integeration test for {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
 * <p>
 * @author Balazs Berkes
 */
public class InitializeWithMockControlIntegrationTest {

    @Mock
    private ThridLevelClassA component1;
    @Mock
    private IndependentObject component2;
    @MockControl
    private IMocksControl control;
    @Injected
    private FacadeWithNonRelatedComponents underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @After
    public void cleanUp() {
        control.reset();
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
        assertNotNull(control);
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

    @Test
    public void testMockAreCreatedByControl() {
        expect(component2.createObject()).andReturn(new Object());
        control.replay();
        component2.createObject();
        control.verify();
    }
}
