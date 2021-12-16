package com.github.alexeylapin.ocp.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CharacterStreamTest {

    @Test
    void name(@TempDir File temp) {
        File file = new File(temp, "file.txt");

        try (var out = new BufferedWriter(new FileWriter(file))) {
            out.write(97);
            out.newLine();
            out.write("test-1");
            out.newLine();
            out.write(new char[] {97, 98, 99});
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try (var in = new BufferedReader(new FileReader(file))) {
            assertThat(in.readLine()).isEqualTo("a");
            assertThat(in.readLine()).isEqualTo("test-1");
            assertThat(in.readLine()).isEqualTo("abc");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

}
