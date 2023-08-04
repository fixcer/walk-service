package dev.toannv.interview.walk.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

class DateUtilsTest {

    @BeforeAll
    static void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }

    @Test
    void whenToLocalDate_shouldReturnLocalDate() {
        final Date date = new Date();
        LocalDate result = DateUtils.toLocalDate(date);

        Assertions.assertEquals(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), result);
    }

    @Test
    void whenToLocalDate_shouldReturnNull() {
        LocalDate result = DateUtils.toLocalDate(null);
        Assertions.assertNull(result);
    }
}