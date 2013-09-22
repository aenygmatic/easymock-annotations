package integrationtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

import integrationtest.support.ThirdLevelClassA;

/**
 * Integration test for {@link EasyMockAnnotations#initialize(Object)}.
 * It should inject the all fields including the inherited ones.
 * <p>
 * @author Balazs Berkes
 */
public class InheritedFieldScannerIntegrationTest extends InheritedFieldScannerIntegrationTestBase {

    @Mock
    private ThirdLevelClassA thridLevelClassA;

    @Injected
    private ThirdLevelClassA underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @Test
    public void testInitializeShouldInjectAllFieldsOfCurrentFields() {
        assertEquals(thridLevelClassA, underTest.getThrirdField());
    }

    @Test
    public void testInitializeShouldInjectAllFieldsOfInheritedFields() {
        assertEquals(firstLevelClass, underTest.getFirstField());
        assertEquals(secondLevelClassA, underTest.getSecondField());
    }
}
