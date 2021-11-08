package com.github.alexeylapin.ocp.nio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class FilesTest {

    @Test
    void name1() {
        assertThat(Files.exists(Path.of("README.md"))).isTrue();
    }

    @Test
    void name2() {
        try {
            Files.isSameFile(Path.of("README.md"), Path.of("fake.txt"));
        } catch (IOException e) {
            assertThat(e).isNotNull().isExactlyInstanceOf(NoSuchFileException.class);
        }
    }

    @Test
    void name3() {
        try {
            assertThat(Files.isSameFile(Path.of("fake.txt"), Path.of("fake.txt"))).isTrue();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void name4(@TempDir Path temp) {
        assertThat(Files.exists(temp)).isTrue();

        Path sub1 = temp.resolve("sub1");

        assertThatCode(() -> Files.createDirectory(sub1)).doesNotThrowAnyException();
    }

    @Test
    void name5(@TempDir Path temp) {
        assertThat(Files.exists(temp)).isTrue();
        assertThat(Files.isRegularFile(temp)).isFalse();
        assertThat(Files.isDirectory(temp)).isTrue();

        Path sub1 = temp.resolve("sub1");
        Path sub2 = sub1.resolve("sub2");
        assertThat(Files.isDirectory(sub1)).isFalse();
        assertThat(Files.isRegularFile(sub1)).isFalse();

        assertThatCode(() -> Files.createDirectory(sub2)).isExactlyInstanceOf(NoSuchFileException.class);
        assertThatCode(() -> Files.createDirectories(sub2)).doesNotThrowAnyException();
        assertThatCode(() -> Files.createDirectories(sub1)).doesNotThrowAnyException();
        assertThat(Files.isDirectory(sub1)).isTrue();
        assertThat(Files.isDirectory(sub2)).isTrue();
    }

    @Test
    void name6(@TempDir Path temp) {
        Path sub1 = temp.resolve("sub1");
        Path sub2 = temp.resolve("sub2");
        assertThat(Files.exists(sub2)).isFalse();
        assertThat(Files.isRegularFile(sub2)).isFalse();
        assertThat(Files.isDirectory(sub2)).isFalse();

        assertThatCode(() -> Files.createDirectory(sub1)).doesNotThrowAnyException();

        assertThatCode(() -> Files.copy(sub1, sub2)).doesNotThrowAnyException();

        assertThat(Files.exists(sub1)).isTrue();
        assertThat(Files.exists(sub2)).isTrue();
        assertThat(Files.isRegularFile(sub2)).isFalse();
        assertThat(Files.isDirectory(sub2)).isTrue();
    }

    @Test
    void name7(@TempDir Path temp) {
        Path sub1 = temp.resolve("sub1");
        Path sub2 = temp.resolve("sub2");
        assertThat(Files.exists(sub2)).isFalse();
        assertThat(Files.isRegularFile(sub2)).isFalse();
        assertThat(Files.isDirectory(sub2)).isFalse();

        assertThatCode(() -> Files.write(sub1, List.of("line1", "line2"))).doesNotThrowAnyException();

        assertThatCode(() -> Files.copy(sub1, sub2)).doesNotThrowAnyException();

        assertThat(Files.exists(sub1)).isTrue();
        assertThat(Files.exists(sub2)).isTrue();
        assertThat(Files.isRegularFile(sub2)).isTrue();
        assertThat(Files.isDirectory(sub2)).isFalse();
    }

    @Test
    void name8(@TempDir Path temp) {
        Path sub1 = temp.resolve("sub1");
        Path sub2 = temp.resolve("sub2");
        assertThat(Files.exists(sub2)).isFalse();
        assertThat(Files.isRegularFile(sub2)).isFalse();
        assertThat(Files.isDirectory(sub2)).isFalse();

        assertThatCode(() -> Files.createDirectory(sub1)).doesNotThrowAnyException();

        assertThatCode(() -> Files.move(sub1, sub2)).doesNotThrowAnyException();

        assertThat(Files.exists(sub1)).isFalse();
        assertThat(Files.exists(sub2)).isTrue();
        assertThat(Files.isRegularFile(sub2)).isFalse();
        assertThat(Files.isDirectory(sub2)).isTrue();
    }

    @Test
    void name9(@TempDir Path temp) {
        Path sub1 = temp.resolve("sub1");
        Path sub2 = temp.resolve("sub2");
        assertThat(Files.exists(sub2)).isFalse();
        assertThat(Files.isRegularFile(sub2)).isFalse();
        assertThat(Files.isDirectory(sub2)).isFalse();

        assertThatCode(() -> Files.write(sub1, List.of("line1", "line2"))).doesNotThrowAnyException();

        assertThatCode(() -> Files.move(sub1, sub2)).doesNotThrowAnyException();

        assertThat(Files.exists(sub1)).isFalse();
        assertThat(Files.exists(sub2)).isTrue();
        assertThat(Files.isRegularFile(sub2)).isTrue();
        assertThat(Files.isDirectory(sub2)).isFalse();
    }

    @Test
    void name10(@TempDir Path temp) {
        Path path = temp.resolve("fake.txt");

        assertThatCode(() -> Files.delete(path)).isExactlyInstanceOf(NoSuchFileException.class);
        assertThatCode(() -> Files.deleteIfExists(path)).doesNotThrowAnyException();

        assertThatCode(() -> Files.write(path, List.of("line1", "line2", "line3"))).doesNotThrowAnyException();

        assertThatCode(() -> Files.delete(path)).doesNotThrowAnyException();
    }

    @Test
    void name11(@TempDir Path temp) {
        Path path = temp.resolve("file.txt");
        try (var writer = Files.newBufferedWriter(path)) {
            writer.write("string1");
            writer.newLine();
            writer.write("string2");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try {
            List<String> lines = Files.readAllLines(path);
            assertThat(lines).containsExactly("string1", "string2");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void name12(@TempDir Path temp) {
        assertThat(Files.isRegularFile(temp)).isFalse();
        assertThat(Files.isDirectory(temp)).isTrue();
        assertThat(Files.isSymbolicLink(temp)).isFalse();

        try {
            assertThat(Files.isHidden(temp)).isFalse();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        assertThat(Files.isWritable(temp)).isTrue();
        assertThat(Files.isReadable(temp)).isTrue();
        assertThat(Files.isExecutable(temp)).isTrue();
    }

    @Test
    void name13(@TempDir Path temp) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(temp, BasicFileAttributes.class);
            assertThat(attributes.isRegularFile()).isFalse();
            assertThat(attributes.isDirectory()).isTrue();
            assertThat(attributes.isSymbolicLink()).isFalse();
            assertThat(attributes.isOther()).isFalse();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void name14(@TempDir Path temp) {
        BasicFileAttributeView view = Files.getFileAttributeView(temp, BasicFileAttributeView.class);
        assertThat(view.name()).isEqualTo("basic");
        try {
            view.setTimes(FileTime.from(Instant.now()), null, null);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void name15() {
        try (Stream<Path> stream = Files.list(Path.of(""))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private long size(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void name16() {
        try (Stream<Path> stream = Files.walk(Path.of(""))) {
            long sum = stream.peek(System.out::println)
                    .filter(Files::isRegularFile)
                    .mapToLong(this::size)
                    .sum();
            System.out.println(sum);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void name17() {
        try (Stream<Path> stream = Files.find(
                Path.of(""),
                Integer.MAX_VALUE,
                (path, __) -> path.getFileName().toString().endsWith(".java"))) {
            long sum = stream.peek(System.out::println)
                    .filter(Files::isRegularFile)
                    .mapToLong(this::size)
                    .sum();
            System.out.println(sum);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
