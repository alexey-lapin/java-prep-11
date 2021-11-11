package com.github.alexeylapin.ocp.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ListTest {

    @Test
    void should_thrownNullPointerException_when_listOfNullElement() {
        assertThatNoException().isThrownBy(() -> List.of(1, 2));

        assertThatNullPointerException().isThrownBy(() -> List.of("1", null, "2"));
    }

    @Test
    void listViews() {
        Integer[] array = {1, 2, 3};
        List<Integer> list0 = Arrays.asList(array);
        List<Integer> list1 = List.of(array);
        List<Integer> list2 = List.copyOf(list0);

        array[0] = 5;

        assertThat(list0.get(0)).isEqualTo(5);
        assertThat(list1.get(0)).isEqualTo(1);
        assertThat(list2.get(0)).isEqualTo(1);

        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> list0.add(4));
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> list1.add(4));
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> list2.add(4));

        list0.set(0, 6);

        assertThat(array[0]).isEqualTo(6);
    }

}
