package com.github.alexeylapin.ocp.g0_serialization;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class Sample07 implements Serializable {

    String s1;
    String s2;

    public Sample07(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    @Override
    public String toString() {
        return "Sample07{s1=" + s1 + ", s2=" + s2 + "}";
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        System.out.println("writing...");
        ObjectOutputStream.PutField fields = oos.putFields();
        fields.put("s1", s1.toUpperCase());
        oos.writeFields();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        System.out.println("reading...");
        ObjectInputStream.GetField fields = ois.readFields();
        String s1 = (String) fields.get("s1", null);
        this.s1 = s1 == null ? null : s1.toLowerCase();
        String s2 = (String) fields.get("s2", null);
        this.s2 = s2 == null ? null : s2.toLowerCase();
    }
}

class T07 {
    @Test
    void name() throws IOException, ClassNotFoundException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);
        oos.writeObject(new Sample07("test1", "test2"));

        var ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Sample07 o = (Sample07) ois.readObject();

        assertThat(o.getS1()).isEqualTo("test1");
        assertThat(o.getS2()).isNull();
    }
}
