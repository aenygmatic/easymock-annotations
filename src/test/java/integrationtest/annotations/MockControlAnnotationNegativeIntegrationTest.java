/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtest.annotations;

import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.MockControl;
import org.easymock.annotation.exception.EasyMockAnnotationInitializationException;

/**
 * Integration test for exception type when {@link MockControl @MockContol} annotation is placed on incorrect type of
 * field.
 * <p>
 * @author Balazs Berkes
 */
public class MockControlAnnotationNegativeIntegrationTest {

    @MockControl
    private Thread incorrectType;

    @Test(expected = EasyMockAnnotationInitializationException.class)
    public void testInitializeShouldThrowExceptinWhenControlTypeIsIncorrect() {
        //GIVEN incorrect field annotated with @MockControl
        //WHEN
        EasyMockAnnotations.initializeWithMockControl(this);
        //THEN exception should be thrown
    }
}
