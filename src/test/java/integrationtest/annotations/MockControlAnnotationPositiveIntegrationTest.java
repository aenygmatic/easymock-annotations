package integrationtest.annotations;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.easymock.IMocksControl;
import org.easymock.MockType;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.MockControl;

/**
 * Integration test for {@link MockControl @MockContol} creation and type parameters.
 * <p>
 * @author Balazs Berkes
 */
public class MockControlAnnotationPositiveIntegrationTest {

    @MockControl(MockType.DEFAULT)
    private IMocksControl defaultControl;
    @MockControl(MockType.NICE)
    private IMocksControl niceControl;
    @MockControl(MockType.STRICT)
    private IMocksControl strictControl;
    @MockControl
    private IMocksControl control;

    @Test
    public void testiInitializeWithMockControlShouldCreateDefaultMock() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.DEFAULT, getMockType(defaultControl));
    }

    @Test
    public void testiInitializeWithMockControlShouldCreateNiceMock() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.NICE, getMockType(niceControl));
    }

    @Test
    public void testiInitializeWithMockControlShouldCreateStrictMock() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.STRICT, getMockType(strictControl));
    }

    @Test
    public void testiInitializeWithMockControlShouldCreateDefaultMockWhenNoParameterIsGiven() {
        EasyMockAnnotations.initialize(this);
        assertEquals(MockType.DEFAULT, getMockType(control));
    }

    private MockType getMockType(Object control) {
        for (Field f : control.getClass().getDeclaredFields()) {
            if (f.getType() == MockType.class) {
                return (MockType) getField(f, control);
            }
        }
        return null;
    }

    private Object getField(Field field, Object target) {
        Object fieldValue = null;
        field.setAccessible(true);
        try {
            fieldValue = field.get(target);
        } catch (Exception ignored) {
        }
        return fieldValue;
    }
}
