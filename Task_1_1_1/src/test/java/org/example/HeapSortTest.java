package org.example;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class HeapSortTest {
    @Test
    void testArray() {
        int[] input = {5, 3, 1, 2, 4};
        int[] expected = {1, 2, 3, 4, 5};

        HeapSort.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void emptyArray() {
        int[] input = {};
        int[] expected = {};

        HeapSort.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void sortedArray() {
        int[] input = {1, 2, 3, 4, 5, 6};
        int[] expected = {1, 2, 3, 4, 5, 6};

        HeapSort.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void arrayWithDuplitcates() {
        int[] input = {2, 3, 1, 2, 3, 4, 2, 1};
        int[] expected = {1, 1, 2, 2, 2, 3, 3, 4};

        HeapSort.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void arrayWithNegativeAndZero() {
        int[] input = {0, 67,    -2, 1, 2, -3};
        int[] expected = {-3, -2, 0, 1, 2, 67};

        HeapSort.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void arrayFromOneElement() {
        int[] input = {5};
        int[] expected = {5};

        HeapSort.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void arrayWithIdenticalElements() {
        int[] input = {1, 1, 1, 1, 1};
        int[] expected = {1, 1, 1, 1, 1};

        HeapSort.sort(input);

        assertArrayEquals(expected, input);
    }


    @Test
    void hugeRandomArray() {
        int[] array = new int[1000];

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            array[i] = random.nextInt();
        }

        int[] expected = array.clone();

        Arrays.sort(expected);
        HeapSort.sort(array);

        assertArrayEquals(expected, array);
    }
}
