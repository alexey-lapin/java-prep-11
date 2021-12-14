package com.github.alexeylapin.ocp.fundamentals;

import org.junit.jupiter.api.Test;

public class VarTest {

    @Test
    void name() {
        var i1 = 1;

        int i2;
        i2 = 1;

        // var a; // does not compile
        // a = 6;
    }

    @Test
    void name1() {
        int a = 1, b = 2, c = 3;

        // var a1 = 1, b1 = 2, c1 = 3;
    }

    @Test
    void name2() {
        int a, b, c;
        a = b = c = 1;

        // var a1, b1, c1; does not compile
        var a1 = 1;
        var b1 = 1;
        var c1 = b1 = a1 = 2;
    }

}
