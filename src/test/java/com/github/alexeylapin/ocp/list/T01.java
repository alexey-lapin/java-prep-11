package com.github.alexeylapin.ocp.list;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class T01 {

    @Test
    void name() {
        List<String> list = new ArrayList<>(); // array size = 0
        System.out.println(list);
        list.add("1"); // array size = 10
        System.out.println(list);
    }

    @Test
    void name2() {
        ArrayList<String> list = new ArrayList<>(30); // array size = 30
        System.out.println(list);
    }
}
