package dev.toannv.interview.walk.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtils {

    /**
     * Convert cursor to steps and stepId
     *
     * @param cursor {@link String} format: steps-stepId
     * @return {@link Pair} of steps and stepId
     */
    public static Pair<Integer, Long> cursorToStepsAndStepId(final String cursor) {
        if (StringUtils.isBlank(cursor)) {
            return Pair.of(0, 0L);
        }

        String[] split = StringUtils.split(cursor, Constants.Character.DASH);
        if (split.length != 2) {
            log.error("Invalid cursor: {}", cursor);
            return Pair.of(0, 0L);
        }

        return Pair.of(NumberUtils.toInt(split[0], 0), NumberUtils.toLong(split[1], 0L));
    }

    /**
     * Convert steps and stepId to cursor
     *
     * @param steps  {@link Integer} steps
     * @param stepId {@link Long} stepId
     * @return {@link String} format: steps-stepId
     */
    public static String toCursor(final int steps, final long stepId) {
        return StringUtils.join(steps, Constants.Character.DASH, stepId);
    }

}
