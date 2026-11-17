package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class AppTest {
    /** Test from task example. */
    @Test
    void testfromTask(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");
        Files.writeString(path, "абракадабра");

        List<Long> result = SubstringFinder.finder(path, "бра");
        List<Long> answer = List.of(1L, 8L);

        assertEquals(answer, result);
    }

    /** Test for huge file and random amount of matches. */
    @Test
    void testHugeFile(@TempDir Path tempDir) throws IOException {
        Random random = new Random();

        Path path = tempDir.resolve("file.txt");
        List<Long> answer = new ArrayList<>();

        long currentIndex = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (int i = 0; i < 100_000; i++) {
                if (random.nextInt(5) == 0) {
                    writer.write("aba");
                    answer.add(currentIndex);
                    currentIndex += 3;
                } else {
                    writer.write("a");
                    currentIndex += 1;
                }
            }
        }

        List<Long> result = SubstringFinder.finder(path, "aba");

        assertEquals(answer, result);
    }

    /** Test for cheking algorithm works on bounds of buffer. */
    @Test
    void testBufferBoundarySplit(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");
        int bufferSize = 64 * 1024;

        String prefix = "x".repeat(bufferSize - 1);
        Files.writeString(path, prefix + "абракадабра");

        List<Long> result = SubstringFinder.finder(path, "абракадабра");
        List<Long> answer = List.of((long) (bufferSize - 1));
        assertEquals(answer, result);
    }

    /** Test for algorithm correctness on overlapps template. */
    @Test
    void testOverlappingTemplate(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");

        Files.writeString(path, "абабаба");

        List<Long> result = SubstringFinder.finder(path, "аба");
        List<Long> answer = List.of(0L, 2L, 4L);
        assertEquals(answer, result);
    }

    /** Test algorithm work on border of text. */
    @Test
    void testBorders(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");

        Files.writeString(path, "sincoscoscossin");

        List<Long> result = SubstringFinder.finder(path, "sin");
        List<Long> answer = List.of(0L, 12L);
        assertEquals(answer, result);
    }

    /** Test for empty file. */
    @Test
    void testEmptyFile(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");

        Files.writeString(path, "");

        List<Long> result = SubstringFinder.finder(path, "sin");
        List<Long> answer = List.of();
        assertEquals(answer, result);
    }

    /** Test for template longer than text. */
    @Test
    void testTemplateLongerThanText(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");

        Files.writeString(path, "qwerty");

        List<Long> result = SubstringFinder.finder(path, "qwertyui");
        List<Long> answer = List.of();
        assertEquals(answer, result);
    }

    /** Test for text has no tempate. */
    @Test
    void testNoMatchings(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");

        Files.writeString(path, "pyramid");

        List<Long> result = SubstringFinder.finder(path, "pop");
        List<Long> answer = List.of();
        assertEquals(answer, result);
    }

    /** Test for correctness error handling. */
    @Test
    void testInvalidInputs(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");
        Files.writeString(path, "текст");

        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.finder(path, ""));
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.finder(path, null));
        assertThrows(
                IOException.class,
                () -> SubstringFinder.finder(tempDir.resolve("missing.txt"), "а"));
    }

    /** Test for CLI entry point. */
    @Test
    void testAppMain(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("file.txt");
        Files.writeString(path, "абракадабра");

        App.main(new String[] {path.toString(), "бра"});
        App.main(new String[] {tempDir.resolve("missing.txt").toString(), "бра"});
    }
}
