package com.github.alexeylapin.ocp.functional;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

public class FunctionPackageTest {

    // consumer         - accepts args          - does not return value
    // predicate        - accepts args          - returns boolean value
    // supplier         - does not accept args  - returns value
    // function         - accepts args          - returns value
    // unaryOperator    - accepts args          - returns value
    // binaryOperator   - accepts args          - returns value
    @Test
    void consumer() {
        Consumer<String> consumer = (arg1) -> {
        };
        consumer.accept("");

        BiConsumer<String, String> biConsumer = (arg1, arg2) -> {
        };
        biConsumer.accept("", "");

        IntConsumer intConsumer = (int i) -> {
        };
        intConsumer.accept(1);

        LongConsumer longConsumer = (long l) -> {
        };
        longConsumer.accept(1L);

        DoubleConsumer doubleConsumer = (double d) -> {
        };
        doubleConsumer.accept(1.0);

        ObjIntConsumer<String> objIntConsumer = (String obj, int i) -> {
        };
        objIntConsumer.accept("", 1);

        ObjLongConsumer<String> objLongConsumer = (String obj, long l) -> {
        };
        objLongConsumer.accept("", 1L);

        ObjDoubleConsumer<String> objDoubleConsumer = (String obj, double d) -> {
        };
        objDoubleConsumer.accept("", 1.0);
    }

    @Test
    void supplier() {
        Supplier<String> supplier = () -> "";
        supplier.get();

        BooleanSupplier booleanSupplier = () -> true;
        booleanSupplier.getAsBoolean();

        IntSupplier intSupplier = () -> 1;
        intSupplier.getAsInt();

        LongSupplier longSupplier = () -> 1;
        longSupplier.getAsLong();

        DoubleSupplier doubleSupplier = () -> 1.9;
        doubleSupplier.getAsDouble();
    }

    @Test
    void predicate() {
        Predicate<String> predicate = (String arg1) -> true;
        predicate.test("");

        BiPredicate<String, String> biPredicate = (String arg1, String arg2) -> true;
        biPredicate.test("", "");

        IntPredicate intPredicate = (int i) -> true;
        intPredicate.test(1);

        LongPredicate longPredicate = (long l) -> true;
        longPredicate.test(1L);

        DoublePredicate doublePredicate = (double d) -> true;
        doublePredicate.test(1.0);
    }

    @Test
    void function() {
        Function<String, Object> function = (arg1) -> new Object();
        function.apply("");

        BiFunction<String, LocalDate, Object> biFunction = (arg1, arg2) -> new Object();
        biFunction.apply("", LocalDate.now());

        UnaryOperator<String> unaryOperator = (arg1) -> "";
        unaryOperator.apply("");

        BinaryOperator<String> binaryOperator = (arg1, arg2) -> "";
        binaryOperator.apply("", "");

        IntUnaryOperator intUnaryOperator = (int i) -> 2;
        intUnaryOperator.applyAsInt(1);

        LongUnaryOperator longUnaryOperator = (long l) -> 2L;
        longUnaryOperator.applyAsLong(2L);

        DoubleUnaryOperator doubleUnaryOperator = (double d) -> 2.0;
        doubleUnaryOperator.applyAsDouble(2.0);

        IntBinaryOperator intBinaryOperator = (int a, int b) -> 3;
        intBinaryOperator.applyAsInt(3, 3);

        LongBinaryOperator longBinaryOperator = (long a, long b) -> 3L;
        longBinaryOperator.applyAsLong(3, 3);

        DoubleBinaryOperator doubleBinaryOperator = (double a, double b) -> 3.0;
        doubleBinaryOperator.applyAsDouble(3.0, 3.0);

        // accept primitive type - return reference type
        IntFunction<Object> intFunction = (int i) -> new Object();
        intFunction.apply(1);

        LongFunction<Object> longFunction = (long l) -> new Object();
        longFunction.apply(1L);

        DoubleFunction<Object> doubleFunction = (double d) -> new Object();
        doubleFunction.apply(1.0);

        // accept reference type - return primitive type
        ToIntFunction<Object> toIntFunction = (obj) -> 1;
        toIntFunction.applyAsInt(new Object());

        ToIntBiFunction<Object, Object> toIntBiFunction = (obj1, obj2) -> 1;
        toIntBiFunction.applyAsInt(new Object(), new Object());

        ToLongFunction<Object> toLongFunction = (obj) -> 1L;
        toLongFunction.applyAsLong(new Object());

        ToLongBiFunction<Object, Object> toLongBiFunction = (obj1, obj2) -> 1L;
        toLongBiFunction.applyAsLong(new Object(), new Object());

        ToDoubleFunction<Object> toDoubleFunction = (obj) -> 1.0;
        toDoubleFunction.applyAsDouble(new Object());

        ToDoubleBiFunction<Object, Object> toDoubleBiFunction = (obj1, obj2) -> 1.0;
        toDoubleBiFunction.applyAsDouble(new Object(), new Object());

        // accept primitive type and return primitive type
        IntToLongFunction intToLongFunction = (int i) -> 1L;
        intToLongFunction.applyAsLong(1);

        IntToDoubleFunction intToDoubleFunction = (int i) -> 1.0;
        intToDoubleFunction.applyAsDouble(1);

        LongToIntFunction longToIntFunction = (long l) -> 1;
        longToIntFunction.applyAsInt(1L);

        LongToDoubleFunction longToDoubleFunction = (long l) -> 1.0;
        longToDoubleFunction.applyAsDouble(1L);

        DoubleToIntFunction doubleToIntFunction = (double d) -> 1;
        doubleToIntFunction.applyAsInt(1.0);

        DoubleToLongFunction doubleToLongFunction = (double d) -> 1L;
        doubleToLongFunction.applyAsLong(1.0);
    }

    @Test
    void consumerMethods() {
        // consumer.andThen()
    }

    @Test
    void functionMethods() {
        // function.andThen()
        // function.compose()
    }

    @Test
    void predicateMethods() {
        // predicate.and()
        // predicate.or()
        // predicate.negate()
    }

    @Test
    void multiArrow() {
        Function<String, Function<Integer, String>> f = s -> i -> "";
        DoubleFunction<DoubleUnaryOperator> doubleFunction = m -> n -> m + n;
    }

    @Test
    void lambdaArgReassign() {
        Consumer<String> c1 = (String s) -> {
            s = "";
        };

        Consumer<String> c2 = (final String s) -> {
            //s = ""; // does not compile
        };

        Consumer<String> c3 = (final var s) -> {
            //s = ""; // does not compile
        };
    }

}
