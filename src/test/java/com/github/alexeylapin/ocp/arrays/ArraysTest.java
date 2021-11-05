package com.github.alexeylapin.ocp.arrays;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ArraysTest {

    @Test
    void exploring_mismatch() {
        assertThatNullPointerException()
                .isThrownBy(() -> Arrays.mismatch((int[]) null, null));

        assertThatNullPointerException()
                .isThrownBy(() -> Arrays.mismatch(new int[]{1}, null));

        assertThatNullPointerException()
                .isThrownBy(() -> Arrays.mismatch(null, new int[]{1}));

        int[] blue = {1};
        // same objects -> no mismatch (-1)
        assertThat(Arrays.mismatch(blue, blue)).isEqualTo(-1);

        int[] red1 = {1};
        int[] red2 = {1};
        // same length, same elements -> no mismatch (-1)
        assertThat(Arrays.mismatch(red1, red2)).isEqualTo(-1);


        // common prefixes
        int[] silver1 = {1, 2, 3, 4};
        int[] silver2 = {1, 2, 5};
        // different length, common prefix of length 0 -> return length of common prefix (0)
        assertThat(Arrays.mismatch(silver1, silver2)).isEqualTo(2);

        int[] white1 = {1, 2};
        int[] white2 = {3, 4, 5};
        // different length, common prefix of length 0 -> return length of common prefix (0)
        assertThat(Arrays.mismatch(white1, white2)).isEqualTo(0);

        int[] yellow1 = {1};
        int[] yellow2 = {2};
        // same length, common prefix of length 0 -> return length of common prefix (0)
        assertThat(Arrays.mismatch(yellow1, yellow2)).isEqualTo(0);

        int[] green1 = {1, 2};
        int[] green2 = {3, 4};
        // same length, common prefix of length 0 -> return length of common prefix (0)
        assertThat(Arrays.mismatch(green1, green2)).isEqualTo(0);


        // proper prefixes
        int[] pink1 = {1, 2};
        int[] pink2 = {1};
        // different length, proper prefix -> return length of the shorter array
        assertThat(Arrays.mismatch(pink1, pink2)).isEqualTo(1);

        int[] black1 = {1, 2, 3};
        int[] black2 = {1};
        // different length, proper prefix -> return length of the shorter array
        assertThat(Arrays.mismatch(black1, black2)).isEqualTo(1);

        int[] gold1 = {1, 2, 3};
        int[] gold2 = {1, 2};
        // different length, proper prefix -> return length of the shorter array
        assertThat(Arrays.mismatch(gold1, gold2)).isEqualTo(2);

        int[] cyan1 = {1, 2, 3};
        int[] cyan2 = {};
        // different length, proper prefix -> return length of the shorter array
        assertThat(Arrays.mismatch(cyan1, cyan2)).isEqualTo(0);
    }

    @Test
    void exploring_compare() {
        int[] magenta = {1};
        // same objects -> arrays are equal
        assertThat(Arrays.compare(magenta, magenta)).isEqualTo(0);

        // null arrays are equal
        assertThat(Arrays.compare((int[]) null, null)).isEqualTo(0);

        // null array is less than non-null array
        assertThat(Arrays.compare(new int[]{1}, null)).isEqualTo(1);

        // null array is less than non-null array
        assertThat(Arrays.compare(null, new int[]{1})).isEqualTo(-1);


        int[] red1 = {1};
        int[] red2 = {1};
        // same length, same elements -> arrays are equal (0)
        assertThat(Arrays.compare(red1, red2)).isEqualTo(0);


        int[] black1 = {1, 2, 3, 4, 5};
        int[] black2 = {1, 2, 4};
        // different length, common prefix of length 2 -> compare elements at index 2 (-1)
        assertThat(Arrays.compare(black1, black2)).isEqualTo(-1);

        int[] green1 = {1, 2, 3, 4, 5};
        int[] green2 = {1, 2, 5};
        // different length, common prefix of length 2 -> compare elements at index 2 (-1)
        assertThat(Arrays.compare(green1, green2)).isEqualTo(-1);

        int[] yellow1 = {1, 2, 3, 4, 5};
        int[] yellow2 = {6, 7, 8};
        // different length, common prefix of length 0 -> compare elements at index 0
        assertThat(Arrays.compare(yellow1, yellow2)).isEqualTo(-1);

        int[] pink1 = {5, 4, 3, 2, 1};
        int[] pink2 = {2, 3, 4};
        // different length, common prefix of length 0 -> compare elements at index 0
        assertThat(Arrays.compare(pink1, pink2)).isEqualTo(1);

        int[] cherry1 = {5, 4, 3};
        int[] cherry2 = {2, 3, 4};
        // same length, common prefix of length 0 -> compare elements at index 0
        assertThat(Arrays.compare(cherry1, cherry2)).isEqualTo(1);


        int[] gray1 = {1};
        int[] gray2 = {1, 2};
        // different length, proper prefix -> return difference in length (-1)
        assertThat(Arrays.compare(gray1, gray2)).isEqualTo(-1);

        int[] white1 = {1};
        int[] white2 = {1, 2, 3};
        // different length, proper prefix -> return difference in length (-2)
        assertThat(Arrays.compare(white1, white2)).isEqualTo(-2);

        int[] brown1 = {};
        int[] brown2 = {1};
        // different length, proper prefix -> return difference in length (-1)
        assertThat(Arrays.compare(brown1, brown2)).isEqualTo(-1);

        int[] blue1 = {};
        int[] blue2 = {1, 2, 3};
        // different length, proper prefix -> return difference in length (-3)
        assertThat(Arrays.compare(blue1, blue2)).isEqualTo(-3);

        int[] cyan1 = {1, 2, 3, 4, 5};
        int[] cyan2 = {1, 2, 3};
        // different length, proper prefix -> return difference in length (2)
        assertThat(Arrays.compare(cyan1, cyan2)).isEqualTo(2);
    }

}
