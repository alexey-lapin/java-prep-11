package com.github.alexeylapin.ocp.nio;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class WalkTest {

    @Test
    void name() throws Exception{
        Files.walk(Path.of(""));
        Files.find(Path.of(""), 1, null);

        Files.walkFileTree(Path.of(""), null);
        Files.newDirectoryStream(Path.of(""));
    }

}
