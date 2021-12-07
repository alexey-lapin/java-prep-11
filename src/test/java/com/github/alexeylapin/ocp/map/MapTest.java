package com.github.alexeylapin.ocp.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class MapTest {

    @Test
    void name() {
        Map<String, String> map = Map.of();

        assertThat(map).isEmpty();
        assertThatCode(() -> map.put("k1", "v1")).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void name1() {
        Map<String, String> map = Map.of("k0", "v0");

        assertThat(map.size()).isEqualTo(1);
        assertThatCode(() -> map.put("k1", "v1")).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void name2() {
        Map<String, Integer> map = new HashMap<>();
        Integer v1 = map.put("k1", 1);
        Integer v3 = map.putIfAbsent("k2", 2);
        Integer v2 = map.get("k1");
        Integer k1 = map.computeIfAbsent("k2", (key) -> 1);
        Integer k2 = map.computeIfPresent("k2", (key, value) -> 1);
        Integer k3 = map.compute("k2", (key, value) -> 1);
        Integer k4 = map.merge("k2", 2, (key, value) -> 1);
        map.replaceAll((key, value) -> 1);

    }


}
