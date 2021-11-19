package com.github.alexeylapin.ocp.functional;

// 4 types
// static method reference
// instance method reference on object
// instance method reference on receiver
// constructor reference

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class MethodReferenceTest {

    static class S {

        static void m0(String... args) {
            System.out.println("m0(String...)");
        }

        static String m2(String... args) {
            System.out.println("m2(String...)");
            return "out";
        }

        static void m1() {
            System.out.println("m1()");
        }

    }

    static class S2 {

        void m1() {
        }

        public void m1(String s1) {
        }

        void m1(String s1, String s2) {
        }

        String m2(String s1) {
            return "out";
        }

        String m0(String... args) {
            return "out";
        }

    }

    interface HexoFunction<T1, T2, T3, T4, T5, T6, R> {

        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);

    }

    @Test
    void example1() {
        Runnable m1 = S::m1;
    }

    @Test
    void example2() {
        Runnable runnable = S::m0;

        Consumer<String> consumer = S::m0;
        BiConsumer<String, String> biConsumer = S::m0;
    }

    @Test
    void staticMethodReferences() {
        Runnable runnable1 = () -> S.m2();
        Runnable runnable2 = S::m2;

        Callable<String> callable1 = () -> S.m2();
        Callable<String> callable2 = S::m2;

        Consumer<String> consumer1 = (arg1) -> S.m2(arg1);
        Consumer<String> consumer2 = S::m2;

        BiConsumer<String, String> biConsumer1 = (arg1, arg2) -> S.m2(arg1);
        BiConsumer<String, String> biConsumer2 = S::m2;

        Supplier<String> supplier1 = () -> S.m2();
        Supplier<String> supplier2 = S::m2;

        Function<String, String> function1 = (arg1) -> S.m2(arg1);
        Function<String, String> function2 = S::m2;

        BiFunction<String, String, String> biFunction1 = (arg1, arg2) -> S.m2(arg1, arg2);
        BiFunction<String, String, String> biFunction2 = S::m2;

        HexoFunction<String, String, String, String, String, String, String> hexoFunction1 = (t1, t2, t3, t4, t5, t6) -> S.m2(t1, t2, t3, t4, t5, t6);
        HexoFunction<String, String, String, String, String, String, String> hexoFunction2 = S::m2;

        UnaryOperator<String> unaryOperator1 = (arg1) -> S.m2(arg1);
        UnaryOperator<String> unaryOperator2 = S::m2;

        BinaryOperator<String> binaryOperator1 = (arg1, arg2) -> S.m2(arg1, arg2);
        BinaryOperator<String> binaryOperator2 = S::m2;
    }

    @Test
    void instanceMethodReferencesOnInstance() {
        S2 s2 = new S2();

        Consumer<String> consumer1 = (str) -> s2.m0(str);
        Consumer<String> consumer2 = s2::m0;


        Function<String, String> function1 = (str) -> s2.m2(str);
        Function<String, String> function2 = s2::m2;
    }

    @Test
    void instanceMethodReferencesOnReceiver() {
        Predicate<String> predicate1 = (String receiver) -> receiver.isEmpty();
        Predicate<String> predicate2 = String::isEmpty;

        Consumer<S2> consumer1 = (S2 receiver) -> receiver.m0();
        Consumer<S2> consumer2 = S2::m0;

        BiConsumer<S2, String> biConsumer1 = (S2 receiver, String arg) -> receiver.m1(arg);
        BiConsumer<S2, String> biConsumer2 = S2::m1;

        Function<S2, String> function1 = (S2 receiver) -> receiver.m0();
        Function<S2, String> function2 = S2::m0;

        BiFunction<S2, String, String> biFunction1 = (S2 receiver, String arg) -> receiver.m2(arg);
        BiFunction<S2, String, String> biFunction2 = S2::m2;
    }

    static class S3 {

        S3() {
        }

        S3(String s1) {
        }

        S3(String s1, String s2) {
        }

    }

    @Test
    void constructor() {
        Supplier<S3> supplier1 = () -> new S3();
        Supplier<S3> supplier2 = S3::new;

        Function<String, S3> function1 = (str) -> new S3(str);
        Function<String, S3> function2 = S3::new;

        BiFunction<String, String, S3> biFunction1 = (str1, str2) -> new S3(str1, str2);
        BiFunction<String, String, S3> biFunction2 = S3::new;
    }

}
