package com.github.alexeylapin.ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class FormatterTest {

    // m, M, d, D, e, y, s, S, h, H, and z

    @Test
    void isoDateFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        LocalDate localDate = LocalDate.of(2022, 5, 5);
        assertThat(formatter.format(localDate)).isEqualTo("2022-05-05");

        LocalTime localTime = LocalTime.of(16, 20, 0);
        // TemporalAccessor must contain year, month, and date info
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

}
