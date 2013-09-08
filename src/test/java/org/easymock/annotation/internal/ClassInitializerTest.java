package org.easymock.annotation.internal;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ClassInitializer}.
 * <p>
 * @author Balazs Berkes
 */
public class ClassInitializerTest {

    private Class<?> clazz;

    private ClassInitializer underTest;

    @Before
    public void setUp() {
        underTest = new ClassInitializer();
    }

    @Test
    public void testInitializeShouldCreateNewInstanceWhenClassHasDefaultConstructor() {
        clazz = ReentrantLock.class;

        Object actualClass = underTest.initialize(clazz);

        assertTrue(actualClass instanceof ReentrantLock);
    }

}
