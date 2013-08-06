package integrationtest.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.TestSubject;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;

/**
 * Integration test for {@link TestSubject @TestSubject} creation and type parameters.
 * <p>
 * @author Balazs Berkes
 */
public class TestSubjectAnnotationIntegrationTest {

    private Object initializedObject = new Object();

    @TestSubject
    private Object preInitInjected = initializedObject;
    @TestSubject
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
