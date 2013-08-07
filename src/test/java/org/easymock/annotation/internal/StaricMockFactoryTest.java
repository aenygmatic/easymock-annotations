/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.internal;

import static org.junit.Assert.assertTrue;

import org.easymock.MockType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link StaricMockFactoryTest}.
 * <p>
 * @author Balazs Berkes
 */
public class StaricMockFactoryTest {

    private MockFactory underTest;

    @Before
    public void setUp() {
        underTest = new StaricMockFactory();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateMockShouldCreateMockWithDefault() {
        //GIVEN
        MockType type = MockType.DEFAULT;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

    @Test
    public void testCreateMockShouldCreateMockWithNice() {
        //GIVEN
        MockType type = MockType.NICE;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

    @Test
    public void testCreateMockShouldCreateMockWithStrict() {
        //GIVEN
        MockType type = MockType.STRICT;
        //WHEN
        Object mock = underTest.createMock(MockFactory.class, type);
        //THEN
        assertTrue(mock instanceof MockFactory);
    }

}
