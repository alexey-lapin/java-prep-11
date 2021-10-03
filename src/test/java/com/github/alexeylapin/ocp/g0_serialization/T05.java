package com.github.alexeylapin.ocp.g0_serialization;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class Sample05 implements Serializable {

    String s;

    public Sample05(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }

    @Override
    public String toString() {
        return "Sample05{s=" + s + "}";
    }

    private Object writeReplace() {
        System.out.println("replacing...");
        return this;
    }

    private void writeObject(ObjectOutputStream oos) {
        System.out.println("writing...");
    }

    private Object readResolve() {
        System.out.println("resolving...");
        return this;
    }

    private void readObject(ObjectInputStream ois) {
        System.out.println("reading...");
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("no data...");
    }
}

class T05 {
    @Test
    void name() throws IOException, ClassNotFoundException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);
        oos.writeObject(new Sample05("test"));

        var ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Sample05 o = (Sample05) ois.readObject();

        assertThat(o.getS()).isEqualTo(null);
    }
}
