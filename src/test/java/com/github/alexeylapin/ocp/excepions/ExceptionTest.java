package com.github.alexeylapin.ocp.excepions;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ExceptionTest {

    @Test
    void should_throwNullPointerException_when_throwNullThrowable() {
        assertThatNullPointerException().isThrownBy(() -> {
            Throwable t = null;
            throw t;
        });
    }

    @Test
    void should_throwNullPointerException_when_throwNullException() {
        assertThatNullPointerException().isThrownBy(() -> {
            Exception ex = null;
            throw ex;
        });
    }

    @Test
    void should_bePossibleToAssignNullToCaughtVariable_when_catchDeclaresOnlyOneType() {
        try {
            throw new CustomIOException();
        } catch (CustomIOException ex) {
            ex.test();
            ex = null;
        }
    }

    @Test
    void should_notBePossibleToAssignNullToCaughtVariable_when_catchDeclaresMultipleTypes() {
        try {
            throw new IOException();
        } catch (IOException | RuntimeException ex) {
//             ex = null; // does not compile
        }
    }

    @Test
    void should_beVisibleOnlyMethodsSharedByAllTypes() {
        try {
            throw new CustomIOException();
        } catch (CustomIOException | RuntimeException ex) {
            // only methods visible for all exceptions in multi-exception block
            ex.printStackTrace();
            // ex.test(); // does not compile
        }
    }

    static class CustomIOException extends IOException {

        String test() {
            return "test";
        }

    }

}
