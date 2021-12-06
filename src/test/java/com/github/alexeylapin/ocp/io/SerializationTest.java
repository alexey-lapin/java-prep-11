package com.github.alexeylapin.ocp.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializationTest {

    // public static final long serialVersionUID = 1;
    // if not specified explicitly will be provided by compiler
    // based on fields and interfaces (what about classes?)
    // affects deserialization
    // if it does not match -> InvalidClassException will be thrown

    // during deserialization initializers and no-args constructor of non-serializable super class called

    static class Example implements Serializable {

        int i = 10;
        String s;

        {
            System.out.println("initializer");
            s = "init";
        }

        public Example() {
            System.out.println("no-args constructor");
        }

    }

    @Test
    void test1() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.out.println("serialization started");
        try (var oos = new ObjectOutputStream(baos)) {
            oos.writeObject(new Example());
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        System.out.println("serialization finished");

        Example deserialized = null;
        System.out.println("deserialization started");
        try (var ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            deserialized = (Example) ois.readObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("deserialization finished");

        assertThat(deserialized.i).isEqualTo(10);
        assertThat(deserialized.s).isEqualTo("init");
    }

    static class A {

        int i0 = 10;
        String s0;

        {
            System.out.println("A initializer");
            s0 = "A init";
        }

        public A() {
            System.out.println("A() no-args constructor");
        }

    }

    static class B extends A {

        int i1 = 11;
        String s1;

        {
            System.out.println("B initializer");
            s1 = "B init";
        }

        public B() {
            System.out.println("B() no-args constructor");
        }

    }

    static class C extends B implements Serializable {

        int i2 = 12;
        String s2;

        {
            System.out.println("C initializer");
            s2 = "C init";
        }

        public C() {
            System.out.println("C() no-args constructor");
        }

    }

    @Test
    void test2() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.out.println("serialization started");
        try (var oos = new ObjectOutputStream(baos)) {
            C obj = new C();
            obj.i0 = 0;
            obj.s0 = "s0";
            obj.i1 = 1;
            obj.s1 = "s1";
            obj.i2 = 2;
            obj.s2 = "s2";
            oos.writeObject(obj);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        System.out.println("serialization finished");

        C deserialized = null;
        System.out.println("deserialization started");
        try (var ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            deserialized = (C) ois.readObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("deserialization finished");

        assertThat(deserialized.i0).isEqualTo(10);
        assertThat(deserialized.s0).isEqualTo("A init");
        assertThat(deserialized.i1).isEqualTo(11);
        assertThat(deserialized.s1).isEqualTo("B init");
        assertThat(deserialized.i2).isEqualTo(2);
        assertThat(deserialized.s2).isEqualTo("s2");
    }

    static class D {
        D(int input) {
            System.out.println("D(" + input + ")");
        }
    }

    static class E extends D implements Serializable {
        E() {
            super(5);
            System.out.println("E()");
        }
    }

    @Test
    void test3() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.out.println("serialization started");
        try (var oos = new ObjectOutputStream(baos)) {
            E obj = new E();
            oos.writeObject(obj);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        System.out.println("serialization finished");

        E deserialized = null;
        System.out.println("deserialization started");
        try (var ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            deserialized = (E) ois.readObject();
        } catch (Exception ex) {
            assertThat(ex).isExactlyInstanceOf(InvalidClassException.class);
            assertThat(deserialized).isNull();
        }
        System.out.println("deserialization finished");
    }

}
