package com.github.alexeylapin.ocp.concurrency.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Support {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void println(String message) {
        System.out.println(FORMATTER.format(LocalDateTime.now()) + " " + Thread.currentThread() + " - " + message);
    }

}
