package com.github.alexeylapin.ocp.i18n;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class LocaleTest {

    public static final String TEST_BUNDLE = "test-bundle";

    public static final String NAMESPACE_1_PROP_0 = "namespace1.prop0";
    public static final String NAMESPACE_1_PROP_1 = "namespace1.prop1";

    private Locale defaultLocale;

    @BeforeEach
    void setUp() {
        defaultLocale = Locale.getDefault();
    }

    @AfterEach
    void tearDown() {
        Locale.setDefault(defaultLocale);
    }

    @Test
    void localeMethodsToSetDefaults() {
        Locale.setDefault(Locale.US);
        Locale.setDefault(Locale.Category.FORMAT, Locale.US);
        Locale.setDefault(Locale.Category.DISPLAY, Locale.US);
    }

    @Test
    void localeConstructors() {
        Locale locale1 = new Locale("de");
        assertThat(locale1.getLanguage()).isEqualTo("de");
        assertThat(locale1.getCountry()).isEmpty();
        assertThat(locale1.getVariant()).isEmpty();

        Locale locale2 = new Locale("de", "DE");
        assertThat(locale2.getLanguage()).isEqualTo("de");
        assertThat(locale2.getCountry()).isEqualTo("DE");
        assertThat(locale2.getVariant()).isEmpty();

        Locale locale3 = new Locale("de", "DE", "variant");
        assertThat(locale3.getLanguage()).isEqualTo("de");
        assertThat(locale3.getCountry()).isEqualTo("DE");
        assertThat(locale3.getVariant()).isEqualTo("variant");
    }

    @Test
    void localeLanguageTag() {
        Locale locale = Locale.forLanguageTag("de-DE-variant");
        assertThat(locale.getLanguage()).isEqualTo("de");
        assertThat(locale.getCountry()).isEqualTo("DE");
        assertThat(locale.getVariant()).isEqualTo("variant");
    }

    @Test
    void localeBuilder() {
        Locale locale = new Locale.Builder()
                .setLanguage("en")
                .setRegion("us")
                .build();

        assertThat(locale.getLanguage()).isEqualTo("en");
        assertThat(locale.getCountry()).isEqualTo("US");
        assertThat(locale.getVariant()).isEmpty();
    }

    @Test
    void resourceBundleForDefaultLocale() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(TEST_BUNDLE);

        assertThat(resourceBundle.getBaseBundleName()).isEqualTo(TEST_BUNDLE);
        assertThat(resourceBundle.getString(NAMESPACE_1_PROP_1)).isEqualTo("value1-default");
    }

    @Test
    void resourceBundleForGermanLocale() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(TEST_BUNDLE, Locale.GERMAN);

        assertThat(resourceBundle.getString(NAMESPACE_1_PROP_1)).isEqualTo("value1-de");
        assertThat(resourceBundle.getString(NAMESPACE_1_PROP_0)).isEqualTo("value0-default");
    }

    @Test
    void resourceBundleKeys() {
        ResourceBundle resourceBundle1 = ResourceBundle.getBundle(TEST_BUNDLE, Locale.GERMAN);

        assertThat(Collections.list(resourceBundle1.getKeys()))
                .containsExactlyInAnyOrder(NAMESPACE_1_PROP_0, NAMESPACE_1_PROP_1);

        ResourceBundle resourceBundle2 = ResourceBundle.getBundle(TEST_BUNDLE, Locale.FRENCH);

        assertThat(Collections.list(resourceBundle2.getKeys()))
                .containsExactlyInAnyOrder(NAMESPACE_1_PROP_0, NAMESPACE_1_PROP_1);
    }

}
