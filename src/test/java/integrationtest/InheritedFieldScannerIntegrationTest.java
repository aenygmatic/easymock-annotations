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

import integrationtest.support.ThirdLevelClassA;
import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Injected;
import org.easymock.annotation.Mock;

/**
 * Integration test for {@link EasyMockAnnotations#initialize(Object)}.
 * It should inject the all fields including the inherited ones.
 * <p>
 * @author Balazs Berkes
 */
public class InheritedFieldScannerIntegrationTest extends InheritedFieldScannerIntegrationTestBase {

    @Mock
    private ThirdLevelClassA thridLevelClassA;

    @Injected
    private ThirdLevelClassA underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
    }

    @Test
    public void testInitializeShouldInjectAllFieldsOfCurrentFields() {
        assertEquals(thridLevelClassA, underTest.getThrirdField());
    }

    @Test
    public void testInitializeShouldInjectAllFieldsOfInheritedFields() {
        assertEquals(firstLevelClass, underTest.getFirstField());
        assertEquals(secondLevelClassA, underTest.getSecondField());
    }
}
