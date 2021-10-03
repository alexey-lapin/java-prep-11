package com.github.alexeylapin.ocp.g0_serialization;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

class T01 {

    @Test
    void name() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
            out.writeInt(1);
        } catch (IOException ex) {

        }
        String s = Arrays.toString(baos.toByteArray());
        System.out.println(s);
    }

}
