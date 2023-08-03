package dev.toannv.interview.walk.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommonUtilsTest {

    @Test
    void givenBlankCursor_whenCursorToStepsAndStepId_thenDefault() {
        final String cursor = StringUtils.EMPTY;
        Pair<Integer, Long> result = CommonUtils.cursorToStepsAndStepId(cursor);
        Assertions.assertEquals(Pair.of(0, 0L), result);
    }

    @Test
    void givenInvalidCursor_whenCursorToStepsAndStepId_thenDefault() {
        final String cursor = "invalid_cursor";
        Pair<Integer, Long> result = CommonUtils.cursorToStepsAndStepId(cursor);
        Assertions.assertEquals(Pair.of(0, 0L), result);
    }

    @Test
    void givenValidCursor_whenCursorToStepsAndStepId_thenValidResult() {
        final String cursor = "10-100";
        Pair<Integer, Long> result = CommonUtils.cursorToStepsAndStepId(cursor);
        Assertions.assertEquals(Pair.of(10, 100L), result);
    }

    @Test
    void givenStepsAndStepId_whenToCursor_thenValidResult() {
        final int steps = 10;
        final long stepId = 100L;
        String result = CommonUtils.toCursor(steps, stepId);
        Assertions.assertEquals("10-100", result);
    }

    @Test
    void givenStepsAndStepId_whenToCursor_thenInvalidResult() {
        final int steps = 10;
        final long stepId = 100L;
        String result = CommonUtils.toCursor(steps, stepId);
        Assertions.assertNotEquals("10-101", result);
    }
}