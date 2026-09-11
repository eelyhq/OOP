package org.example;

/**
 * Class, implemented Heap Sort algorithm.
 */
public class HeapSort {
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void siftDown(int[] array, int heapSize, int vertex){

        while (2 * vertex + 1 < heapSize) {
            int l = 2 * vertex + 1;
            int r = 2 * vertex + 2;

            int maxSonIndex = l;

            if (r < heapSize && array[r] > array[l]) {
                maxSonIndex = r;
            }

            if (array[vertex] >= array[maxSonIndex]) {
                break;
            }

            swap(array, vertex, maxSonIndex);
            vertex = maxSonIndex;
        }
    }

    private static void buildHeap(int[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, n, i);
        }
    }

    /**
     * Function for sorting in-place.
     */
    public static int[] sort(int[] array) {
        int n = array.length;

        buildHeap(array);

        for (int i = n - 1; i >= 0; i--) {
            swap(array, 0, i);
            siftDown(array, i, 0);
        }

        return array;
    }
}
