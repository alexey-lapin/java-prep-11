package com.github.alexeylapin.ocp.g0_serialization;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

// ObjectOutputStream#writeObject throws NotSerializableException
// when it attempts to write instance of the class
// that does not implement {@link java.io.Serializable}

class SampleClass02 {
    int i;
}

class T02 {
    @Test
    void name() throws IOException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);

        Throwable throwable = catchThrowable(() -> oos.writeObject(new SampleClass02()));

        assertThat(throwable).isInstanceOf(NotSerializableException.class);
    }
}
