package com.github.alexeylapin.ocp.set;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class T01 {

    @Test
    void name() {
        Set<Object> set = new TreeSet<>();
        set.add(new Object());
    }

    @Test
    void name2() {
        Set<T01A> set = new TreeSet<>();
        logAndAdd(set, new T01A(2));
        logAndAdd(set, new T01A(5));
        logAndAdd(set, new T01A(10));
        logAndAdd(set, new T01A(-5));
        logAndAdd(set, new T01A(0));
        logAndAdd(set, new T01A(2));
        logAndAdd(set, new T01A(11));

        System.out.println(set);
    }

    @Test
    void name3() {
        Set<T01A> set = new HashSet<>();
        T01A item2 = new T01A(2);
        logAndAdd(set, item2);
        logAndAdd(set, new T01A(5));
        logAndAdd(set, new T01A(10));
        logAndAdd(set, new T01A(-5));
        logAndAdd(set, new T01A(0));
        logAndAdd(set, item2);
        logAndAdd(set, new T01A(11));

        System.out.println(set);
    }

    @Test
    void name4() {
        Set<T01B> set = new HashSet<>();
        logAndAdd(set, new T01B(1));
        logAndAdd(set, new T01B(2));
        logAndAdd(set, new T01B(3));
        logAndAdd(set, new T01B(4));
        logAndAdd(set, new T01B(5));
        logAndAdd(set, new T01B(6));
        logAndAdd(set, new T01B(7));
        logAndAdd(set, new T01B(8));
        logAndAdd(set, new T01B(9));
        logAndAdd(set, new T01B(10));
        logAndAdd(set, new T01B(11));
        logAndAdd(set, new T01B(12));

        System.out.println("Set" + set);
    }

    private static <T> void logAndAdd(Set<T> set, T item) {
        System.out.println("add " + item);
        set.add(item);
    }
}
