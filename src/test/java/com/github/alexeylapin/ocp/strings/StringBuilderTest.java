package com.github.alexeylapin.ocp.strings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringBuilderTest {


    @Test
    void appendTest() {
        StringBuilder sb = new StringBuilder("1234");

        sb.append("5");

        assertThat(sb.toString()).isEqualTo("12345");
    }

    @Test
    void insertTest() {
        StringBuilder sb = new StringBuilder("1234");

        sb.insert(0, "56");

        assertThat(sb.toString()).isEqualTo("561234");

        sb.insert(2, "78");

        assertThat(sb.toString()).isEqualTo("56781234");
    }

    @Test
    void deleteTest() {
        StringBuilder sb = new StringBuilder("1234");

        sb.delete(1, 2);

        assertThat(sb.toString()).isEqualTo("134");
    }

    @Test
    void replaceTest() {
        StringBuilder sb = new StringBuilder("1234");

        sb.replace(1, 2, "567");

        assertThat(sb.toString()).isEqualTo("156734");
    }

    @Test
    void capacityAndLengthTest() {
        StringBuilder sb = new StringBuilder();

        assertThat(sb.capacity()).isEqualTo(16);
        assertThat(sb.length()).isEqualTo(0);

        sb.ensureCapacity(10);

        assertThat(sb.capacity()).isEqualTo(16);
        assertThat(sb.length()).isEqualTo(0);

        sb.ensureCapacity(20);

        assertThat(sb.capacity()).isEqualTo(34);
        assertThat(sb.length()).isEqualTo(0);

        sb.setLength(5);

        assertThat(sb.capacity()).isEqualTo(34);
        assertThat(sb.length()).isEqualTo(5);
    }

}
