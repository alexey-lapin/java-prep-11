package com.github.alexeylapin.ocp.nio;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PathsTest {

    @Test
    void name1() {
        Path path = Path.of("file.txt");
    }

    @Test
    void name2() {
        Path path = Paths.get("file.txt");
    }

    @Test
    void name3() {
        FileSystem fileSystem = FileSystems.getDefault();
        Path path = fileSystem.getPath("file.txt");

    }

    @Test
    void name4() {
        Path path = new File("file.txt").toPath();
    }

    @Test
    void name5() {
        Path path = Path.of("README.md");

        assertThat(path.isAbsolute()).isFalse();
        assertThat(path.getParent()).isNull();
        assertThat(path.getRoot()).isNull();

        assertThat(path.toString()).isEqualTo("README.md");
        assertThat(path.getNameCount()).isEqualTo(1);
        assertThat(path.getName(0)).isEqualTo(path);
    }

    @Test
    void name6() {
        Path path = Path.of("README.md").toAbsolutePath();

        assertThat(path.isAbsolute()).isTrue();
        assertThat(path.getParent()).isNotNull();
        assertThat(path.getRoot()).isNotNull();

        assertThat(path.getNameCount()).isGreaterThan(1);

        Path root = path.getRoot();
        assertThat(root.getParent()).isNull();
        assertThat(root.getRoot()).isEqualTo(root);
        assertThat(root.getNameCount()).isEqualTo(0);
    }

    @Test
    void name7() {
        Path path = Path.of("README.md").toAbsolutePath();

        int nameCount = path.getNameCount();
        assertThat(path.subpath(nameCount - 1, nameCount)).isEqualTo(path.getFileName());

        assertThatThrownBy(() -> path.subpath(nameCount - 1, nameCount - 1));
        assertThatThrownBy(() -> path.subpath(nameCount - 1, nameCount + 1));
    }

    @Test
    void name8() {
        Path path = Path.of("base1");

        assertThat(path.resolve("sub1")).isEqualTo(Path.of("base1", "sub1"));
        assertThat(path.resolveSibling("base2")).isEqualTo(Path.of("base2"));
    }

    @Test
    void name9() {
        Path path1 = Path.of("base1", "sub1");
        Path path2 = Path.of("base2", "sub2", "sub3");

        assertThat(path1.relativize(path2)).isEqualTo(Path.of("..", "..", "base2", "sub2", "sub3"));
        assertThat(path2.relativize(path1)).isEqualTo(Path.of("..", "..", "..", "base1", "sub1"));
        assertThat(path2.relativize(path2)).isEqualTo(Path.of(""));

        assertThat(path1.toAbsolutePath().relativize(path2.toAbsolutePath())).isEqualTo(Path.of("..", "..", "base2", "sub2", "sub3"));
        assertThat(path2.toAbsolutePath().relativize(path1.toAbsolutePath())).isEqualTo(Path.of("..", "..", "..", "base1", "sub1"));
        assertThat(path2.toAbsolutePath().relativize(path2.toAbsolutePath())).isEqualTo(Path.of(""));

        assertThatThrownBy(() -> path2.relativize(path1.toAbsolutePath()));
    }

    @Test
    void name10() {
        Path path = Path.of("base1", "sub1", "..", "sub2", ".", "file.txt");

        assertThat(path.normalize()).isEqualTo(Path.of("base1", "sub2", "file.txt"));
    }

    @Test
    void name11() {
        try {
            Path.of("base1", "sub1", "..", "sub2", ".", "file.txt").toRealPath();
        } catch (IOException e) {
            assertThat(e).isNotNull();
        }
    }

    @Test
    void name12() {
        Path path1 = Path.of("C");
        System.out.println(path1);

        assertThat(path1.getNameCount()).isEqualTo(1);
        assertThat(path1.isAbsolute()).isFalse();
        assertThat(path1.getRoot()).isNull();


        Path path2 = Path.of("C:");
        System.out.println(path2);

        assertThat(path2.getNameCount()).isEqualTo(0);
        assertThat(path2.isAbsolute()).isFalse();
        assertThat(path2.getRoot()).isNotNull();


        Path path3 = Path.of("C:\\");
        System.out.println(path3);

        assertThat(path3.getNameCount()).isEqualTo(0);
        assertThat(path3.isAbsolute()).isTrue();
        assertThat(path3.getRoot()).isNotNull();

//        Path path4 = Path.of("/");
//        System.out.println(path4);
//
//        assertThat(path4.getNameCount()).isEqualTo(0);
//        assertThat(path4.isAbsolute()).isTrue();
//        assertThat(path4.getRoot()).isNotNull();

        assertThat(path2).isNotEqualTo(path3);

        assertThatCode(() -> Path.of("C:\\C:")).isExactlyInstanceOf(InvalidPathException.class);
    }

    @Test
    void name13() {
        FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
        Path path = fileSystem.getPath("/");

        assertThat(path.getNameCount()).isEqualTo(0);
        assertThat(path.isAbsolute()).isTrue();
        assertThat(path.getRoot()).isNotNull();
    }

    @Test
    void name14() {
        Path path1 = Path.of("some");
        Path path2 = Path.of("\\some");
        Path path3 = Path.of("some\\");
        Path path4 = Path.of("\\some\\");
        Path path5 = Path.of("\\some\\\\");
        Path path6 = Path.of("\\some\\\\\\");

        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);
        System.out.println(path4);
        System.out.println(path5);
        System.out.println(path6);

        assertThatCode(() -> Path.of("\\\\some")).isExactlyInstanceOf(InvalidPathException.class);
        assertThatCode(() -> Path.of("\\\\\\some")).isExactlyInstanceOf(InvalidPathException.class);

        assertThat(path1).isNotEqualTo(path2);
        assertThat(path1).isEqualTo(path3);
        assertThat(path2).isEqualTo(path4).isEqualTo(path5).isEqualTo(path6);
    }

    @Test
    void name15() {
        Path path1 = Path.of("some");
        Path path2 = Path.of("/some");
        Path path3 = Path.of("some/");
        Path path4 = Path.of("/some/");
        Path path5 = Path.of("/some//");
        Path path6 = Path.of("/some///");

        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);
        System.out.println(path4);
        System.out.println(path5);
        System.out.println(path6);

        assertThatCode(() -> Path.of("//some")).isExactlyInstanceOf(InvalidPathException.class);
        assertThatCode(() -> Path.of("///some")).isExactlyInstanceOf(InvalidPathException.class);

        assertThat(path1).isNotEqualTo(path2);
        assertThat(path1).isEqualTo(path3);
        assertThat(path2).isEqualTo(path4).isEqualTo(path5).isEqualTo(path6);
    }

    @Test
    void name() {
        assertThat(Path.of("some/other/another").startsWith("some")).isTrue();
        assertThat(Path.of("/some/other/another").startsWith("some")).isFalse();
        assertThat(Path.of("some/other/another").startsWith("some/other")).isTrue();
        assertThat(Path.of("some/other/another").startsWith("some\\other")).isTrue();
        assertThat(Path.of("some/other/another").startsWith("some/other/")).isTrue();
        assertThat(Path.of("some/other/another").startsWith("some\\other/")).isTrue();

        assertThat(Path.of("some/other/another").endsWith("another")).isTrue();
        assertThat(Path.of("some/other/another/").endsWith("another")).isTrue();
        assertThat(Path.of("some/other/another").endsWith("another/")).isTrue();
        assertThat(Path.of("some/other/another").endsWith("/another")).isFalse();
    }

}
