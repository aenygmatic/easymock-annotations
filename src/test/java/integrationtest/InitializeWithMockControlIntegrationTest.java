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
import integrationtest.support.ThirdLevelClassA;

/**
 * Integration test for {@link EasyMockAnnotations#initialize(Object)}.
 * <p>
 * @author Balazs Berkes
 */
public class InitializeWithMockControlIntegrationTest {

    @Mock
    private ThirdLevelClassA component1;
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
        assertNotNull(underTest.getThirdLevelClassA());
        assertNotNull(underTest.getIndependentObject());
    }

    @Test
    public void testInitializeShouldCreateMockAndWriteBackToTestClassFields() {
        assertNotNull(component1);
        assertNotNull(component2);
        assertNotNull(control);
    }

    @Test
    public void testInitializeShouldInjectSameMockToTestClassAndUnderTest() {
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
