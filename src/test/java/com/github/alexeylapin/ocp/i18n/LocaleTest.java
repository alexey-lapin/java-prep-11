package com.github.alexeylapin.ocp.i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

public class LocaleTest {

    @Test
    void name() {
        Locale.setDefault(Locale.US);
        Locale.setDefault(Locale.Category.FORMAT, Locale.US);
    }

    @Test
    void name2() {
        Locale.forLanguageTag("en");
        new Locale("ru");
        new Locale("ru", "RU");
        new Locale("ru", "RU", "variant");
    }

    @Test
    void name3() {
        Locale locale = new Locale.Builder().setLanguage("en").setRegion("us").build();
    }
}
