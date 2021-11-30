package com.github.alexeylapin.ocp.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileStreamTest {

    @Test
    void basic(@TempDir File temp) {

        File file = new File(temp, "out");

        try (var out = new FileOutputStream(file)) {
            out.write(200);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        FileInputStream in;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }

        int read;
        try (in) {
            read = in.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        assertThat(read).isEqualTo(200);
    }

}
