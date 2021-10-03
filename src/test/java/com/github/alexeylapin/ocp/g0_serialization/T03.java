package com.github.alexeylapin.ocp.g0_serialization;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Sample03 implements Serializable {
    int i;
}

class T03 {
    @Test
    void name() throws IOException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);

        oos.writeObject(new Sample03());
    }
}
