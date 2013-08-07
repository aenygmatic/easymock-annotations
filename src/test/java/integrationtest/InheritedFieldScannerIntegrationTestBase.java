package integrationtest;

import org.easymock.annotation.Mock;

import integrationtest.support.FirstLevelClass;
import integrationtest.support.SecondLevelClassA;

/**
 * Integeration test for {@link EasyMockAnnotations#initializeWithMockControl(Object)}.
 * It should inject the all fields including the inherited ones.
 * <p>
 * @author Balazs Berkes
 */
public class InheritedFieldScannerIntegrationTestBase {

    @Mock
    FirstLevelClass firstLevelClass;
    @Mock
    SecondLevelClassA secondLevelClassA;
}
