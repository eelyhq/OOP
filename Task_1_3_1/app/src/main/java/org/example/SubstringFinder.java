package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Substring finder based on Knuth-Morris-Pratt algorithm. */
public class SubstringFinder {
    private static final int BUFF_SIZE = 64 * 1024;

    private static int[] buildLps(String template) {
        if (template == null || template.isEmpty()) {
            throw new IllegalArgumentException("template must be not null and not empty");
        }

        int len = template.length();
        int[] lps = new int[len];

        lps[0] = 0;

        int j = 0;
        for (int i = 1; i < len; i++) {
            char letter = template.charAt(i);

            while (j > 0 && letter != template.charAt(j)) {
                j = lps[j - 1];
            }

            if (letter == template.charAt(j)) {
                j++;
            }

            lps[i] = j;
        }
        return lps;
    }

    /** Static method for finding substring indices in file text. */
    public static List<Long> finder(Path path, String template) throws IOException {
        int[] lps = buildLps(template);
        char[] charTemplate = template.toCharArray();
        ArrayList<Long> result = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            char[] buf = new char[BUFF_SIZE];
            int charsRead;

            int j = 0;
            long globalIndex = 0;
            while ((charsRead = reader.read(buf)) != -1) {
                for (int i = 0; i < charsRead; i++) {
                    char letter = buf[i];

                    while (j > 0 && letter != charTemplate[j]) {
                        j = lps[j - 1];
                    }

                    if (letter == charTemplate[j]) {
                        j++;
                    }

                    if (j == charTemplate.length) {
                        result.add(globalIndex - charTemplate.length + 1);
                        j = lps[j - 1];
                    }
                    globalIndex++;
                }
            }
        }
        return result;
    }
}
