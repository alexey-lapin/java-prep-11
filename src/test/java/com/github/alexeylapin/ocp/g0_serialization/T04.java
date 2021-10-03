package com.github.alexeylapin.ocp.g0_serialization;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class Sample04A implements Serializable {
    int i;
    Sample04B b;

    public Sample04A(Sample04B b) {
        this.b = b;
    }
}

class Sample04B {
    int j;
}

class T04 {
    @Test
    void name() throws IOException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);

        Throwable throwable = catchThrowable(() -> oos.writeObject(new Sample04A(new Sample04B())));

        assertThat(throwable).isInstanceOf(NotSerializableException.class);
    }

    @Test
    void name2() throws IOException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);

        Throwable throwable = catchThrowable(() -> oos.writeObject(new Sample04A(null)));

        assertThat(throwable).isNull();
    }
}
