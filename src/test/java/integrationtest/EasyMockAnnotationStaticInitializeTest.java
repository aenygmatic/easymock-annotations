/* 
 * Copyright 2013 Balazs Berkes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

import integrationtest.support.FacadeWithNonRelatedComponents;
import integrationtest.support.IndependentObject;
import integrationtest.support.ThirdLevelClassA;

/**
 * Integration test for initializing of testclass and tested class.
 * <p>
 * @author Balazs Berkes
 */
public class EasyMockAnnotationStaticInitializeTest {

    @Mock
    private ThirdLevelClassA component1;
    @Mock
    private IndependentObject component2;
    @Injected
    private FacadeWithNonRelatedComponents underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @Test
    public void testInitializeShouldCreateMockAndInjectThem() {
        assertNotNull(underTest.getThirdLevelClassA());
        assertNotNull(underTest.getIndependentObject());
    }

    @Test
    public void testInitializeShouldCreateMockAndWriteBackToTestClassFields() {
        assertNotNull(component1);
        assertNotNull(component2);
    }

    @Test
    public void testInitializeShouldInjectSameMockToTestClassAndUnderTest() {
        assertEquals(component1, underTest.getThirdLevelClassA());
        assertEquals(component2, underTest.getIndependentObject());
    }
}
