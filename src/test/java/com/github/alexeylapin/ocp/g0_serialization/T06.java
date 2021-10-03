package com.github.alexeylapin.ocp.g0_serialization;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class Sample06 implements Serializable {

    String s;

    public Sample06(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    @Override
    public String toString() {
        return "Sample06{s=" + s + "}";
    }

    private Object writeReplace() {
        System.out.println("replacing...");
        return this;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        System.out.println("writing...");
        oos.defaultWriteObject();
    }

    private Object readResolve() {
        System.out.println("resolving...");
        return this;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        System.out.println("reading...");
        ois.defaultReadObject();
    }
}

class T06 {
    @Test
    void name() throws IOException, ClassNotFoundException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);
        oos.writeObject(new Sample06("test"));

        var ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Sample06 o = (Sample06) ois.readObject();

        assertThat(o.getS()).isEqualTo("test");
    }
}
