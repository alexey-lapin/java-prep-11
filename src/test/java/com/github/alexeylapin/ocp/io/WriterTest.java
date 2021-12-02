package com.github.alexeylapin.ocp.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class WriterTest {

    @Test
    void nullWriter() {
        Writer writer = Writer.nullWriter();
    }

    @Test
    void basic(@TempDir File temp) {
        File file = new File(temp, "file.txt");
        try (Writer writer = new FileWriter(file)) {
            writer.write(1);
            writer.write("string");
            writer.write(new char[]{97, 98, 99});
            writer.write(new char[]{'d', 'e', 'f'}, 1, 1);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int read = reader.read();
            System.out.println(read);
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void reader() {
        try (Reader reader = new StringReader("input")) {
            System.out.println((char) reader.read());
            if (reader.markSupported()) {
                reader.mark(2);
                System.out.println((char) reader.read());
                System.out.println((char) reader.read());
                System.out.println((char) reader.read());
                reader.reset();
            }
            System.out.println((char) reader.read());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        StringWriter writer = new StringWriter();
        writer.write("string");

        String result = writer.toString();
    }

    @Test
    void bufferedReaderReadingChars() {
        try (Reader reader = new BufferedReader(new StringReader("input"))) {
            System.out.println((char) reader.read());
            if (reader.markSupported()) {
                reader.mark(2);
                System.out.println((char) reader.read());
                System.out.println((char) reader.read());
                System.out.println((char) reader.read());
                reader.reset();
            }
            System.out.println((char) reader.read());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void bufferedReaderReadingCharArray() {
        try (Reader reader = new BufferedReader(new StringReader("input"))) {
            System.out.println((char) reader.read());
            if (reader.markSupported()) {
                reader.mark(2);
                char[] cbuf = new char[3];
                reader.read(cbuf);
                System.out.println(Arrays.toString(cbuf));
                reader.reset();
            }
            System.out.println((char) reader.read());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // OutputStreamWriter example
    @Test
    void outputstreamWriter() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write("string");
            writer.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        byte[] bytes = out.toByteArray();
        assertThat(bytes.length).isEqualTo("string".length() + System.lineSeparator().length());
    }

}
