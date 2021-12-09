package com.github.alexeylapin.ocp.classes;

import org.junit.jupiter.api.Test;

public class InheritanceTest {

    interface I1 {
        void m1();
    }

    interface I2 {
        void m1();
    }

    interface I3 {
        default void m1() {
            System.out.println("I3");
        }
    }

    interface I4 {
        default void m1() {
            System.out.println("I3");
        }
    }

    static class C1 implements I1, I2 {
        @Override
        public void m1() {

        }
    }

    abstract class C2 implements I1, I2 {
    }

    // static class C3 implements I1, I3 {} // does not compile

    // static class C3 implements I3, I4 {} // does not compile

    interface I5 {
        default void m1() {
            System.out.println("I5");
        }
    }

    interface I6 extends I5 {
        default void m1() {
            System.out.println("I6");
        }
    }

    static class C4 implements I5, I6 {
    }

    static class C5 implements I6, I5 {
    }

    @Test
    void name() {
        new C4().m1();
        new C5().m1();
    }

    public class TestClass{
        public void method(Object o){
            System.out.println("Object Version");
        }
        public void method(java.io.FileNotFoundException s){
            System.out.println("java.io.FileNotFoundException Version");
        }
        public void method(java.io.IOException s){
            System.out.println("IOException Version");
        }

        void m2(Object o) {
            System.out.println(o);
        }
        void m2(Number o) {
            System.out.println(o);
        }
        void m2(Integer o) {
            System.out.println(o);
        }

        void m3(Object o) {

        }
        void m3(Number n) {

        }
        void m3(String s) {

        }
    }

    @Test
    void name2() {
        TestClass tc = new TestClass();
        tc.method(null);
        tc.m2(null);
        tc.m3((String) null); // ambiguous call without cast
    }
}
