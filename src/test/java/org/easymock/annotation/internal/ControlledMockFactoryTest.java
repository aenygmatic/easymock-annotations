/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easymock.annotation.internal;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.easymock.IMocksControl;
import org.easymock.MockType;
import org.junit.Before;
import org.junit.Test;

import org.easymock.annotation.EasyMockAnnotations;
import org.easymock.annotation.Mock;

/**
 * Unit test for {@link ControlledMockFactory}.
 * <p>
 * @author Balazs Berkes
 */
public class ControlledMockFactoryTest {

    private static final String MOCK_NAME = "mockname";

    @Mock
    private IMocksControl control;
    @Mock
    private Thread mock;

    private MockFactory underTest;

    @Before
    public void setUp() {
        EasyMockAnnotations.initialize(this);
        underTest = new ControlledMockFactory(control);
    }

    @Test
    public void testCreateMockShouldCreateMockRegardlessOfType() {
        //GIVEN
        expect(control.createMock(Thread.class)).andReturn(mock);
        replay(control);
        //WHEN
        underTest.createMock(Thread.class, MockType.DEFAULT);
        //THEN
        verify(control);
    }

    @Test
    public void testCreateMockShouldCreateMockWithName() {
        //GIVEN
        expect(control.createMock(MOCK_NAME, Thread.class)).andReturn(mock);
        replay(control);
        //WHEN
        underTest.createMock(Thread.class, MockType.DEFAULT, MOCK_NAME);
        //THEN
        verify(control);
    }
}
