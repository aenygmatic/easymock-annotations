package integrationtest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

import integrationtest.support.ThridLevelClassA;

/**
 * Integeration test for {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
 * It should inject the all fields including the inherited ones.
 * <p>
 * @author Balazs Berkes
 */
public class InheritedFieldScannerIntegrationTest extends InheritedFieldScannerIntegrationTestBase {

    @Mock
    private ThridLevelClassA thridLevelClassA;

    @Injected
    private ThridLevelClassA underTest;

    @Test
    public void testInitializeShouldInjectAllFields() {
        //GIVEN annotated fields in test class.
        //WHEN
        EasyMockAnnotations.initialize(this);
        //THEN
        assertInheritedFieldsAreInjected();
    }

    protected void assertInheritedFieldsAreInjected() {
        assertEquals(firstLevelClass, underTest.getFirstField());
        assertEquals(secondLevelClassA, underTest.getSecondField());
        assertEquals(thridLevelClassA, underTest.getThrirdField());
    }
}
