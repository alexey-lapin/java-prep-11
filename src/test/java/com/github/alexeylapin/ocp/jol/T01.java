package com.github.alexeylapin.ocp.jol;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.util.ArrayList;
import java.util.List;

public class T01 {

    @Test
    void name() {
        String s1 = ClassLayout.parseClass(int[].class).toPrintable();
        System.out.println(s1);
    }

    @Test
    void name2() {
        String s2 = GraphLayout.parseInstance((Object) new int[]{1, 2, 3}).toPrintable();
        System.out.println(s2);
    }

    @Test
    void name3() throws Exception {
        GraphLayout.parseInstance((Object) new int[]{1, 2, 3}).toImage("img.png");
    }

    public static volatile Object sink;

    @Test
    void name4() throws Exception {
            System.out.println(VM.current().details());

            // allocate some objects to beef up generations
            for (int c = 0; c < 1000000; c++) {
                sink = new Object();
            }
            System.gc();

            List<String> list = new ArrayList<>();
            for (int c = 0; c < 1000; c++) {
                list.add("Key" + c);
            }

            for (int c = 1; c <= 10; c++) {
                GraphLayout.parseInstance(list).toImage("list-" + c + ".png");
                System.gc();
            }
    }
}
