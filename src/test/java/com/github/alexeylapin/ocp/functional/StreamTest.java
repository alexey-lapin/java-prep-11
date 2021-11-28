package com.github.alexeylapin.ocp.functional;

import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.awaitility.Awaitility.await;

public class StreamTest {

    @Test
    void simpleFiniteStreams() {
        Stream<Integer> stream0 = Stream.empty();
        stream0.forEach(System.out::println);

        Stream<Integer> stream1 = Stream.of(1);
        stream1.forEach(System.out::println);

        Stream<Integer> stream2 = Stream.of(1, 2, 3);
        stream2.forEach(System.out::println);
    }

    @Test
    void streamOfThrowsWhenArgIsNull() {
        assertThatNullPointerException().isThrownBy(() -> Stream.of(null));
        assertThatCode(() -> Stream.of(1, null, 3)).doesNotThrowAnyException();
    }

    @Test
    void streamOfDoesNotThrowWhenSomeArgsAreNulls() {
        Stream<Integer> stream = Stream.of(1, null, 3);
        stream.forEach(System.out::println);
    }

    @Test
    void streamsFromCollection() {
        List<Integer> list = List.of(1, 2, 3);
        Stream<Integer> stream1 = list.stream();
        Stream<Integer> stream2 = list.parallelStream();

        Set<Integer> set = Set.of(1, 2, 3);
        Stream<Integer> stream3 = set.stream();
        Stream<Integer> stream4 = set.parallelStream();
    }

    @Test
    void infiniteStreams() {
        Stream<Integer> stream1 = Stream.generate(() -> ThreadLocalRandom.current().nextInt());
        Stream<Integer> stream2 = Stream.iterate(1, i -> i + 1);
        Stream<Integer> stream3 = Stream.iterate(1, i -> i < 100, i -> i + 1);
    }

    // count()

    // min()
    // max()

    // findAny()
    // findFirst()

    // allMatch()
    // anyMatch()
    // noneMatch()

    // forEach()
    // reduce()
    // collect()

    @Test
    void should_finishCountingFiniteStream() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        long count = stream.count();

        assertThat(count).isEqualTo(3);
    }

    @Test
    void should_notFinishCountingInfiniteStream() {
        Stream<Integer> stream = Stream.generate(() -> ThreadLocalRandom.current().nextInt());

        assertThatExceptionOfType(ConditionTimeoutException.class)
                .isThrownBy(() -> await().atMost(Duration.ofSeconds(5)).until(() -> stream.count() > 0));
    }

    // Optional<T>  reduce(BinaryOperator<T> accumulator)
    // T            reduce(T identity, BinaryOperator<T> accumulator)
    // <U> U        reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)

    @Test
    void should_reduceIntgersWithAccumulator() {
        Stream<Integer> stream = Stream.of(1, 2, 3);

        Optional<Integer> reduce = stream.reduce((a, b) -> a + b);

        assertThat(reduce).contains(6);
    }

    @Test
    void should_reduceStringsWithAccumulator() {
        Stream<String> stream = Stream.of("a", "b", "c");

        Optional<String> reduce = stream.reduce((a, b) -> a + b);

        assertThat(reduce).contains("abc");
    }

    @Test
    void should_reduceEmptyStreamOfStringsWithAccumulator() {
        Stream<String> stream = Stream.of();

        Optional<String> reduce = stream.reduce((a, b) -> a + b);

        assertThat(reduce).isEmpty();
    }

    @Test
    void should_reduceStringsWithIdentityAndAccumulator() {
        Stream<String> stream = Stream.of("a", "b", "c");

        String result = stream.reduce("initial-", (a, b) -> a + b);

        assertThat(result).contains("initial-abc");
    }

    @Test
    void should_reduceStringsWithIdentityAndAccumulatorAndCombiner_when_identityIsString() {
        Stream<String> stream = Stream.of("a", "b", "c");

        String result = stream.reduce("initial-", (res, obj) -> res + obj, (a, b) -> a + b);

        assertThat(result).contains("initial-abc");
    }

    @Test
    void should_reduceStringsWithIdentityAndAccumulatorAndCombiner_when_identityIsStringBuilder() {
        Stream<String> stream = Stream.of("a", "b", "c");

        StringBuilder result = stream.reduce(
                new StringBuilder("initial-"),
                (StringBuilder res, String obj) -> res.append(obj),
                (StringBuilder a, StringBuilder b) -> a.append(b)
        );

        assertThat(result.toString()).isEqualTo("initial-abc");
    }

    // <R> R    collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
    // <R, A> R collect(Collector<? super T, A, R> collector);

    @Test
    void should_collectIntegersWithSupplierAndAccumulatorAndCombiner() {
        ArrayList<Integer> collect = Stream.of(1, 2, 3)
                .collect(() -> new ArrayList<Integer>(), (list, element) -> list.add(element), (list1, list2) -> {
                });
        System.out.println(collect);
    }

    @Test
    void collectors() {
        Collector<CharSequence, ?, String> collector1 = Collectors.joining();
        Collector<CharSequence, ?, String> collector2 = Collectors.joining(",");
        Collector<CharSequence, ?, String> collector3 = Collectors.joining(",", "prefix-", "-suffix");

        String collect1 = Stream.of("1", "2", "3").collect(collector1);
        String collect2 = Stream.of("1", "2", "3").collect(collector2);
        String collect3 = Stream.of("1", "2", "3").collect(collector3);

        System.out.println(collect1);
        System.out.println(collect2);
        System.out.println(collect3);

        assertThatNullPointerException().isThrownBy(() -> {
            Collectors.partitioningBy(null);
            Collectors.partitioningBy(null, null);

            Collectors.toCollection(null);
            Collectors.toList();
            Collectors.toSet();

            Collectors.toMap(null, null);
            Collectors.toMap(null, null, null);
            Collectors.toMap(null, null, null, null);

            Collectors.toUnmodifiableList();
            Collectors.toUnmodifiableSet();
            Collectors.toUnmodifiableMap(null, null);
            Collectors.toUnmodifiableMap(null, null, null);

            Collectors.toConcurrentMap(null, null);
            Collectors.toConcurrentMap(null, null, null);
            Collectors.toConcurrentMap(null, null, null, null);
        });
    }

    @Test
    void numeric() {
        Collector<String, ?, IntSummaryStatistics> collector1 = Collectors.summarizingInt(Integer::parseInt);
        Collector<String, ?, LongSummaryStatistics> collector2 = Collectors.summarizingLong(Long::parseLong);
        Collector<String, ?, DoubleSummaryStatistics> collector3 = Collectors.summarizingDouble(Double::parseDouble);

        IntSummaryStatistics collect1 = Stream.of("1", "2", "3").collect(collector1);
        LongSummaryStatistics collect2 = Stream.of("1", "2", "3").collect(collector2);
        DoubleSummaryStatistics collect3 = Stream.of("1", "2", "3").collect(collector3);

        System.out.println(collect1);
        System.out.println(collect2);
        System.out.println(collect3);


        Collector<String, ?, Integer> collector4 = Collectors.summingInt(Integer::parseInt);
        Collector<String, ?, Long> collector5 = Collectors.summingLong(Long::parseLong);
        Collector<String, ?, Double> collector6 = Collectors.summingDouble(Double::parseDouble);

        Integer collect4 = Stream.of("1", "2", "3").collect(collector4);
        Long collect5 = Stream.of("1", "2", "3").collect(collector5);
        Double collect6 = Stream.of("1", "2", "3").collect(collector6);

        System.out.println(collect4);
        System.out.println(collect5);
        System.out.println(collect6);


        Collector<String, ?, Double> collector7 = Collectors.averagingInt(Integer::parseInt);
        Collector<String, ?, Double> collector8 = Collectors.averagingLong(Long::parseLong);
        Collector<String, ?, Double> collector9 = Collectors.averagingDouble(Double::parseDouble);

        Collectors.counting();

        Collectors.maxBy(Integer::compare);
        Collectors.minBy(Integer::compare);
    }

    static class Item {

        private int id;
        private String name;
        private Category category;

        public Item(int id, String name, Category category) {
            this.id = id;
            this.name = name;
            this.category = category;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Category getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", category=" + category +
                    '}';
        }
    }

    static class Category {

        private String name;

        public Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    void groupingBy() {
//        Collectors.groupingBy(null);
//        Collectors.groupingBy(null, null);
//        Collectors.groupingBy(null, null, null);

        Category category1 = new Category("cat-1");
        Category category2 = new Category("cat-2");
        Category category3 = new Category("cat-3");

        Stream<Item> stream = Stream.of(
                new Item(1, "item-1", category1),
                new Item(2, "item-2", category1),
                new Item(3, "item-3", category2)
        );

        Map<String, List<Item>> collect1 = stream.collect(Collectors.groupingBy(item -> item.getName()));
        System.out.println(collect1);

        Stream<Item> stream2 = Stream.of(
                new Item(1, "item-1", category1),
                new Item(2, "item-2", category1),
                new Item(3, "item-3", category2)
        );

        Map<String, List<Item>> collect2 = stream2.collect(Collectors.groupingBy(item -> item.getCategory().getName()));
        System.out.println(collect2);

        Stream<Item> stream3 = Stream.of(
                new Item(1, "item-1", category1),
                new Item(2, "item-2", category1),
                new Item(3, "item-3", category2)
        );

        Map<String, Long> collect3 = stream3.collect(Collectors.groupingBy(
                item -> item.getCategory().getName(),
                Collectors.counting()
        ));
        System.out.println(collect3);
    }

    @Test
    void mapping() {
//        Collectors.mapping(null, null);
        Category category1 = new Category("cat-1");
        Category category2 = new Category("cat-2");
        Category category3 = new Category("cat-3");

        Stream<Item> stream = Stream.of(
                new Item(1, "item-1", category1),
                new Item(2, "item-2", category1),
                new Item(3, "item-3", category2)
        );

        List<String> collect1 = stream.collect(Collectors.mapping(item -> item.getName(), Collectors.toList()));
        System.out.println(collect1);

        Stream<Item> stream2 = Stream.of(
                new Item(1, "item-1", category1),
                new Item(2, "item-2", category1),
                new Item(3, "item-3", category2)
        );

        Map<String, Set<String>> collect2 = stream2.collect(Collectors.groupingBy(
                item -> item.getCategory().getName(),
                Collectors.mapping(item -> item.getName(), Collectors.toSet())
        ));
        System.out.println(collect2);
    }

    @Test
    void intStream() {
        Stream.of(1, 2, 3).max(Integer::compareTo);
        IntStream.of(1, 2, 4).max();
    }

    @Test
    void doubleStream() {
        double sum = DoubleStream.of(1, 2, 3)
                .filter(d -> d % 2 == 0)
                .sum();
    }

}
