package org.easymock.annotation.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ByGenericSelector}.
 * <p>
 * @author Balazs Berkes
 */
public class ByGenericSelectorTest {

    public Map<String, Object> stringObjectMap;
    public Map<Integer, String> integerStringMap;
    public Map<String, Integer> stringIntegerMap;
    public List<String> stringList;

    private MockHolder stringObjectHolder;
    private MockHolder integerStringHolder;
    private List<MockHolder> mocks;
    private Field targetField;

    private ByGenericSelector underTest;

    @Before
    public void setUp() throws NoSuchFieldException {
        initializeMocks();
        underTest = new ByGenericSelector();
    }

    @Test
    public void testGetMatchingMocksShouldReturnOnlyMockWithSameGenericParameters() {
        givenMocks(stringObjectHolder, integerStringHolder);
        givenTargetField("stringObjectMap");

        List<MockHolder> matchingMocks = underTest.getMatchingMocks(targetField, mocks);

        assertEquals(stringObjectHolder, matchingMocks.get(0));
    }

    @Test
    public void testGetMatchingMocksShouldNotSelectWhenLessGenericParameterInTarget() {
        givenMocks(stringObjectHolder, integerStringHolder);
        givenTargetField("stringList");

        List<MockHolder> matchingMocks = underTest.getMatchingMocks(targetField, mocks);

        assertTrue(matchingMocks.isEmpty());
    }

    @Test
    public void testGetMatchingMocksShouldNotSelectWhenGenericParamOrderIsDifferent() {
        givenMocks(stringObjectHolder, integerStringHolder);
        givenTargetField("stringIntegerMap");

        List<MockHolder> matchingMocks = underTest.getMatchingMocks(targetField, mocks);

        assertTrue(matchingMocks.isEmpty());
    }

    private void initializeMocks() throws NoSuchFieldException {
        stringObjectMap = new HashMap<String, Object>();
        stringObjectHolder = new MockHolder();
        stringObjectHolder.setSourceField(this.getClass().getField("stringObjectMap"));

        integerStringMap = new HashMap<Integer, String>();
        integerStringHolder = new MockHolder();
        integerStringHolder.setSourceField(this.getClass().getField("integerStringMap"));

    }

    private void givenMocks(MockHolder... mocks) {
        this.mocks = Arrays.asList(mocks);
    }

    private void givenTargetField(String string) {
        try {
            targetField = this.getClass().getField(string);
        } catch (NoSuchFieldException ex) {
        }
    }
}
