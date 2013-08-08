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
        MockType type = MockType.DEFAULT;
        expect(control.createMock(Thread.class)).andReturn(mock);
        replay(control);
        //WHEN
        underTest.createMock(Thread.class, type);
        //THEN
        verify(control);
    }
}