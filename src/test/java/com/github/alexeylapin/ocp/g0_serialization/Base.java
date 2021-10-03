package com.github.alexeylapin.ocp.g0_serialization;

import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Supplier;

public class Base {

    private ByteArrayOutputStream baos;

    protected ObjectOutputStream oos;
    protected Supplier<ObjectInputStream> oiss;

    @BeforeEach
    void setUp() throws IOException {
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oiss = () -> {
            try {
                return new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
