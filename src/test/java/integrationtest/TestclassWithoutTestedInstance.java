package integrationtest;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Mock;

/**
 * Integration test for {@link EasyMockAnnotations#initialize(Object)}.
 * When no tested class initialized nothing should happen.
 * <p>
 * @author Balazs Berkes
 */
public class TestclassWithoutTestedInstance {

    @Mock
    private Object mock;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @Test
    public void testInitializeShouldOnlyCreateMockWhenNoTestedClass() {
        assertNotNull(mock);
    }
}
