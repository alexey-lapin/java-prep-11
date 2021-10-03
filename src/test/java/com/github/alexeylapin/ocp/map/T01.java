package com.github.alexeylapin.ocp.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class T01 {

    @Test
    void name() {
//        T01A item2 = new T01A(2);
        Map<T01A, String> map = new HashMap<>();
        logAndPut(map, new T01A(2), "s1");
        logAndPut(map, new T01A(2), "s2");
        logAndPut(map, new T01A(2), "s3");
        logAndPut(map, new T01A(2), "s4");
        logAndPut(map, new T01A(2), "s5");

        System.out.println(map);
    }

    @Test
    void name2() {
        Map<T01B, String> map = new HashMap<>();
        logAndPut(map, new T01B(2), "s1");
        logAndPut(map, new T01B(2), "s2");
        logAndPut(map, new T01B(2), "s3");
        logAndPut(map, new T01B(2), "s4");
        logAndPut(map, new T01B(2), "s5");

        System.out.println(map);
    }

    @Test
    void name3() {
        Map<T01B, String> map = new HashMap<>();
        logAndPut(map, new T01B(2), "s1");
        logAndPut(map, new T01B(4), "s2");
        logAndPut(map, new T01B(-5), "s3");
        logAndPut(map, new T01B(0), "s4");
        logAndPut(map, new T01B(11), "s5");

        System.out.println(map);
    }

    @Test
    void name4() {
        Map<T01B, String> map = new HashMap<>();
        logAndPut(map, new T01B(1), "s1");
        logAndPut(map, new T01B(2), "s2");
        logAndPut(map, new T01B(3), "s3");
        logAndPut(map, new T01B(4), "s4");
        logAndPut(map, new T01B(5), "s5");
        logAndPut(map, new T01B(6), "s6");
        logAndPut(map, new T01B(7), "s7");
        logAndPut(map, new T01B(8), "s8");
        logAndPut(map, new T01B(9), "s9");
        logAndPut(map, new T01B(10), "s10");
        logAndPut(map, new T01B(11), "s11");
        logAndPut(map, new T01B(12), "s12");
        logAndPut(map, new T01B(13), "s12");

        System.out.println(map);
    }

    @Test
    void name5() {
        Map<T01C, String> map = new HashMap<>();
        logAndPut(map, new T01C(1), "s1");
        logEntryClass(map);
        logAndPut(map, new T01C(2), "s2");
        logEntryClass(map);
        logAndPut(map, new T01C(3), "s3");
        logEntryClass(map);
        logAndPut(map, new T01C(4), "s4");
        logEntryClass(map);
        logAndPut(map, new T01C(5), "s5");
        logEntryClass(map);
        logAndPut(map, new T01C(6), "s6");
        logEntryClass(map);
        logAndPut(map, new T01C(7), "s7");
        logEntryClass(map);
        logAndPut(map, new T01C(8), "s8");
        logEntryClass(map);
        logAndPut(map, new T01C(9), "s9");
        logEntryClass(map);
        logAndPut(map, new T01C(10), "s10");
        logEntryClass(map);
        logAndPut(map, new T01C(11), "s11");
        logEntryClass(map);
        logAndPut(map, new T01C(12), "s12");
        logEntryClass(map);
        logAndPut(map, new T01C(13), "s13");
        logEntryClass(map);

        System.out.println(map);
    }

    private static void logEntryClass(Map<?,?> map) {
        System.out.println(map.entrySet().stream().findFirst().get().getClass().getName());
    }

    private static <K, V> void logAndPut(Map<K, V> map, K key, V value) {
        System.out.println(">> put " + key.toString() + " " + value.toString());
        map.put(key, value);
    }

}
