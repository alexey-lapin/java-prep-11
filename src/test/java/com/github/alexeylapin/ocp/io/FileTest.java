package com.github.alexeylapin.ocp.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileTest {

    @Test
    void name() {
        String fs1 = System.getProperty("file.separator");
        String fs2 = File.separator;

        assertThat(fs1).isEqualTo(fs2);
    }

    @Test
    void name2(@TempDir File tempDir) {
        System.out.println(tempDir);

        String srcTestJavaDir = System.getProperty("gradle.src.test.java.dir");
        System.out.println(srcTestJavaDir);

        File f1 = new File("qwer");
        File f2 = new File("README.md");
        File f3 = new File(srcTestJavaDir, "com");
        File f4 = new File((String) null, "README.md");
        File f5 = new File(tempDir, "absent.txt");
        File f6 = new File((File) null, "absent.txt");

        assertThat(f1.exists()).isFalse();
        assertThat(f2.exists()).isTrue();
        assertThat(f3.exists()).isTrue();
        assertThat(f4.exists()).isTrue();
        assertThat(f5.exists()).isFalse();
        assertThat(f6.exists()).isFalse();
    }

    @Test
    void name3() {
        File file = new File("README.md");

        assertThat(file.isFile()).isTrue();
        assertThat(file.isDirectory()).isFalse();
        assertThat(file.length()).isGreaterThan(0);
        assertThat(file.getName()).isEqualTo("README.md");
        assertThat(file.getPath()).isEqualTo("README.md");
        assertThat(file.getAbsolutePath().length()).isGreaterThan(file.getPath().length());
        assertThat(file.getParent()).isNull();
        assertThat(file.getAbsoluteFile().getParent()).isNotNull();
    }

    @Test
    void name4(@TempDir File temp) {
        assertThat(temp.exists()).isTrue();
        assertThat(temp.isDirectory()).isTrue();
        assertThat(temp.mkdir()).isFalse();
        assertThat(temp.mkdirs()).isFalse();


        File some1 = new File(temp, "some1");
        assertThat(some1.exists()).isFalse();
        assertThat(some1.delete()).isFalse();
        assertThat(some1.mkdir()).isTrue();
        assertThat(some1.exists()).isTrue();
        assertThat(some1.isDirectory()).isTrue();
        assertThat(some1.delete()).isTrue();


        File some2 = new File(temp, "some2");
        File some3 = new File(some2, "some3");
        assertThat(some2.exists()).isFalse();
        assertThat(some3.exists()).isFalse();

        assertThat(some3.mkdir()).isFalse();
        assertThat(some3.mkdirs()).isTrue();
        assertThat(some2.exists()).isTrue();
        assertThat(some2.isDirectory()).isTrue();
        assertThat(some3.exists()).isTrue();
        assertThat(some3.isDirectory()).isTrue();

        File some4 = new File(temp, "some4");
        assertThat(some2.renameTo(some4)).isTrue();
        assertThat(some2.delete()).isFalse();
        assertThat(some3.delete()).isFalse();
        assertThat(some2.delete()).isFalse();

        assertThat(some4.delete()).isFalse();
        assertThat(new File(some4, "some3").delete()).isTrue();
        assertThat(some4.delete()).isTrue();
    }

}
