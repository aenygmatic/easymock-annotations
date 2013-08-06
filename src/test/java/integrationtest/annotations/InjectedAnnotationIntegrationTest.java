package integrationtest.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;

/**
 * Integration test for {@link Injected @Injected} creation and type parameters.
 * <p>
 * @author Balazs Berkes
 */
public class InjectedAnnotationIntegrationTest {

    private Object initializedObject = new Object();

    @Injected
    private Object preInitInjected = initializedObject;
    @Injected
    private Object injected;

    @Test
    public void testInitializeShouldNotCreateNewObjectWhenInjectedFieldAlreadyInstantiated() {
        EasyMockAnnotations.initialize(this);
        assertEquals(initializedObject, preInitInjected);
    }

    @Test
    public void testInitializeShouldCreateNewObjectWhenInjectedFieldIsNotInstantiated() {
        EasyMockAnnotations.initialize(this);
        assertNotNull(injected);
        assertNotEquals(initializedObject, injected);
    }
}
