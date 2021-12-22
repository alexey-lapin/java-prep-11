package com.github.alexeylapin.ocp.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class FormatterTest {

    // m, M, d, D, e, y, s, S, h, H, and z

    @Test
    void isoDateFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        String formatted = formatter.format(LocalDate.of(2022, 5, 5));

        assertThat(formatted).isEqualTo("2022-05-05");
    }

    @Test
    void isoDateTimeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        String formatted = formatter.format(LocalDateTime.of(2022, 5, 5, 16, 20, 0));

        assertThat(formatted).isEqualTo("2022-05-05T16:20:00");
    }

}
