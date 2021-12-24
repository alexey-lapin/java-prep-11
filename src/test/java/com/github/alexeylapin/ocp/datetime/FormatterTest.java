package com.github.alexeylapin.ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class FormatterTest {

    // m, M, d, D, e, y, s, S, h, H, and z

    @Test
    void isoDateFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        LocalDate localDate = LocalDate.of(2022, 5, 5);
        assertThat(formatter.format(localDate)).isEqualTo("2022-05-05");

        LocalTime localTime = LocalTime.of(16, 20, 0);
        // TemporalAccessor must contain year, month, and day info
        assertThatExceptionOfType(UnsupportedTemporalTypeException.class)
                .isThrownBy(() -> formatter.format(localTime));
    }

    @Test
    void isoDateTimeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 5, 16, 20, 0);
        assertThat(formatter.format(localDateTime)).isEqualTo("2022-05-05T16:20:00");

        LocalDate localDate = LocalDate.of(2022, 5, 5);
        // TemporalAccessor must contain hour, minute, and second info
        assertThatExceptionOfType(UnsupportedTemporalTypeException.class)
                .isThrownBy(() -> formatter.format(localDate));
    }

    @Test
    void customFormatYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        LocalDate localDate = LocalDate.of(2022, 5, 5);
        assertThat(formatter.format(localDate)).isEqualTo("2022");

        LocalTime localTime = LocalTime.of(16, 20, 0);
        // TemporalAccessor must contain year info
        assertThatExceptionOfType(UnsupportedTemporalTypeException.class)
                .isThrownBy(() -> formatter.format(localTime));
    }

    @Test
    void customFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 5, 16, 20, 0);
        assertThat(formatter.format(localDateTime)).isEqualTo("2022-05-05 16:20:00");

        LocalTime localTime = LocalTime.of(16, 20, 0);
        // TemporalAccessor must contain year, month, and day info
        assertThatExceptionOfType(UnsupportedTemporalTypeException.class)
                .isThrownBy(() -> formatter.format(localTime));
    }

}
