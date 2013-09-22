package integrationtest;

import org.easymock.annotation.Mock;

import integrationtest.support.FirstLevelClass;
import integrationtest.support.SecondLevelClassA;

/**
 * Integration test for {@link EasyMockAnnotations#initialize(Object)}.
 * It contains annotated fields which will be scanned from subclass.
 * <p>
 * @author Balazs Berkes
 */
public class InheritedFieldScannerIntegrationTestBase {

    @Mock
    FirstLevelClass firstLevelClass;
    @Mock
    SecondLevelClassA secondLevelClassA;
}
