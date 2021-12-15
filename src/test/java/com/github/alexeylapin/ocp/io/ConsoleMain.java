package com.github.alexeylapin.ocp.io;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Arrays;

public class ConsoleMain {

    public static void main(String[] args) {
        Console console = System.console();
        System.out.println(console);
        System.out.println(System.console());

        console.printf("printf: %s %2.2f%n", "string", 2.5);
        console.format("printf: %s %2.2f%n", "string", 66.555);

        String line1 = console.readLine();
        console.printf("you entered: %s%n", line1);
        String line2 = console.readLine("enter %s and press enter: ", "some characters");
        console.printf("you entered: %s%n", line2);

        char[] chars1 = console.readPassword();
        console.printf("you entered: %s%n", Arrays.toString(chars1));
        char[] chars2 = console.readPassword("enter %s and press enter: ", "password");
        console.printf("you entered: %s%n", Arrays.toString(chars2));

        Reader reader = console.reader();
        System.out.println(reader);

        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            bufferedReader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        PrintWriter writer = console.writer();
        System.out.println(writer);

        writer.println("formatted");
    }

}
