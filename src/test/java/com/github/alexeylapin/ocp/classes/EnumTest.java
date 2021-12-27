package com.github.alexeylapin.ocp.classes;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {

    @Test
    void emptyEnum() {
        assertThat(TestEnum1.values()).isEmpty();
    }

    @Test
    void simpleEnum() {
        assertThat(TestEnum2.values()).containsExactlyInAnyOrder(TestEnum2.ITEM_1, TestEnum2.ITEM_2);
    }

    @Test
    void simpleEnumValueOf() {
        assertThat(TestEnum2.valueOf("ITEM_2")).isEqualTo(TestEnum2.ITEM_2);
    }

    @Test
    void enumWithFields() {
        assertThat(TestEnum3.ITEM_1.getInfo()).isEqualTo("item-1");

        TestEnum3.ITEM_1.setInfo("override");

        assertThat(TestEnum3.ITEM_1.getInfo()).isEqualTo("override");
    }

    @Test
    void enumImplementingInterface() {
        assertThat(TestEnum4.ITEM1.get()).isEqualTo("item-1");
        assertThat(TestEnum4.ITEM2.get()).isEqualTo("item-2");
    }

    enum TestEnum1 {
    }

    enum TestEnum2 {
        ITEM_1,
        ITEM_2
    }

    enum TestEnum3 {
        ITEM_1("item-1"),
        ITEM_2("item-2");

        String info; // not final field - is a code smell

        TestEnum3(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }

        // however it is possible to have mutable enum items
        public void setInfo(String info) {
            this.info = info;
        }

    }

    enum TestEnum4 implements Supplier<String> {

        ITEM1 {
            @Override
            public String get() {
                return "item-1";
            }
        },
        ITEM2 {
            @Override
            public String get() {
                return "item-2";
            }
        }

    }

}
