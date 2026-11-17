package org.example;

import java.io.IOException;
import java.nio.file.Path;

/** Default Class. */
public class App {
    /** Enter point. */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java App <path> <template>");
            System.exit(1);
        }

        Path path = Path.of(args[0]);

        try {
            var indices = SubstringFinder.finder(path, args[1]);
            System.out.println(indices);
        } catch (IOException e) {
            System.err.println("Error to read file: " + e.getMessage());
        }
    }
}
