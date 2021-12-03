package com.github.alexeylapin.ocp.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TryWithResourcesTest {

    @Test
    void reassignTest() {
        try (InputStream is = new ByteArrayInputStream(new byte[0])) {
            // is = new ByteArrayInputStream(new byte[0]); // does not compile - resources are implicitly final
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        try (final InputStream is = new ByteArrayInputStream(new byte[0])) {
            // is = new ByteArrayInputStream(new byte[0]); // does not compile
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    static class TrowingClosable implements Closeable {

        public TrowingClosable(Runnable constructorAction) {
            constructorAction.run();
        }

        @Override
        public void close() throws IOException {
            throw new IOException("close");
        }
    }

    @Test
    void should_haveSuppressedException() {
        Throwable t = null;

        try (var closeable = new TrowingClosable(() -> {
        })) {
            throw new RuntimeException("try-body");
        } catch (Throwable ex) {
            t = ex;
        }

        assertThat(t)
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("try-body")
                .hasSuppressedException(new IOException("close"));
    }

    @Test
    void should_notHaveSuppressedException() {
        Throwable t = null;

        try (var closeable = new TrowingClosable(() -> {
            throw new RuntimeException("try-resource");
        })) {
            throw new RuntimeException("try-body");
        } catch (Throwable ex) {
            t = ex;
        }

        assertThat(t)
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("try-resource")
                .hasNoSuppressedExceptions();
    }

}
