package integrationtest;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Mock;

/**
 * Integeration test for {@link EasyMockAnnotations#iniitlize(Object)}.
 * When no tested class initialized nothing should happen.
 * <p>
 * @author Balazs Berkes
 */
public class TestclassWithoutTestedInstance {

    @Mock
    private Object mock;

    @Test
    public void testInitializeShouldOnlyCreateMockWhenNoTestedClass() {
        EasyMockAnnotations.initialize(this);

        assertNotNull(mock);
    }
}
