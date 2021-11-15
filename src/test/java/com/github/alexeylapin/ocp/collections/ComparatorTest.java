package com.github.alexeylapin.ocp.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {

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
    void comparatorTest() {
        List<Item> items = new ArrayList<>(List.of(
                new Item(1, "item-1", new Category("cat1")),
                new Item(2, "item-2", new Category("cat2")),
                new Item(3, "item-3", new Category("cat3")),
                new Item(4, "item-4", new Category("cat1"))
        ));
        Collections.shuffle(items);
        items.sort(Comparator.comparing(item -> item.getName()));

        items.sort(Comparator.<Item, String>comparing(item -> item.getCategory().getName())
                .thenComparing(item -> item.getName())
                .reversed()
        );
        System.out.println(items);

        items.sort(Comparator.<Item, String>comparing(item -> item.getCategory().getName())
                .thenComparing(item -> item.getName(), Comparator.reverseOrder())
        );
        System.out.println(items);
    }

}
