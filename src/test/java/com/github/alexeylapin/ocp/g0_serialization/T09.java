package com.github.alexeylapin.ocp.g0_serialization;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class T09 extends Base {

    // objects are shared during serialization process
    @SuppressWarnings("unchecked")
    @Test
    void name() throws IOException, ClassNotFoundException {
        List<String> list = new ArrayList<>(List.of("s1", "s2", "s3"));
        oos.writeObject(list);
        list.add("s4");
        oos.writeObject(list);

        var ois = oiss.get();
        List<String> l1 = (List<String>) ois.readObject();
        List<String> l2 = (List<String>) ois.readObject();

        assertThat(l1).containsExactly("s1", "s2", "s3");
        assertThat(l2).containsExactly("s1", "s2", "s3");
        assertThat(l1 == l2).isTrue();
        assertThat(l1.get(0) == l2.get(0)).isTrue();
    }

    // writeUnshared prevent caching of top level objects
    @SuppressWarnings("unchecked")
    @Test
    void name2() throws IOException, ClassNotFoundException {
        List<String> list = new ArrayList<>(List.of("s1", "s2", "s3"));
        oos.writeUnshared(list);
        list.add("s4");
        oos.writeUnshared(list);

        var ois = oiss.get();
        List<String> l1 = (List<String>) ois.readObject();
        List<String> l2 = (List<String>) ois.readObject();

        assertThat(l1).containsExactly("s1", "s2", "s3");
        assertThat(l2).containsExactly("s1", "s2", "s3", "s4");
        assertThat(l1 == l2).isFalse();
        assertThat(l1.get(0) == l2.get(0)).isTrue();
    }

    // readUnshared does not work with shared objects
    @SuppressWarnings("unchecked")
    @Test
    void name3() throws IOException, ClassNotFoundException {
        List<String> list = new ArrayList<>(List.of("s1", "s2", "s3"));
        oos.writeObject(list);
        list.add("s4");
        oos.writeObject(list);

        var ois = oiss.get();
        List<String> l1 = (List<String>) ois.readUnshared();
        Throwable throwable = catchThrowable(() -> ois.readUnshared());

        assertThat(l1).containsExactly("s1", "s2", "s3");
        assertThat(throwable).isInstanceOf(InvalidObjectException.class);
    }

    // nested objects are shared anyway
    @SuppressWarnings("unchecked")
    @Test
    void name4() throws IOException, ClassNotFoundException {
        List<String> list = new ArrayList<>(List.of("s1", "s2", "s3"));
        oos.writeUnshared(list);
        list.add("s4");
        oos.writeUnshared(list);
        oos.writeUnshared(List.of("s1"));

        ObjectInputStream ois = oiss.get();
        List<String> l1 = (List<String>) ois.readUnshared();
        List<String> l2 = (List<String>) ois.readUnshared();
        List<String> l3 = (List<String>) ois.readUnshared();

        assertThat(l1).containsExactly("s1", "s2", "s3");
        assertThat(l2).containsExactly("s1", "s2", "s3", "s4");
        assertThat(l1 == l2).isFalse();
        assertThat(l1.get(0) == l2.get(0)).isTrue();
        assertThat(l1.get(0) == l3.get(0)).isTrue();
    }

}
