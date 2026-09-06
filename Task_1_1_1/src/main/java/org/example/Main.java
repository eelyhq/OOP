package org.example;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 *  Main class, used for heap sorting complexity proving.
 */
public class Main {
    /**
     * Entry point.
     */
    static void main(String[] args) {
        // Complexity proving
        Random random = new Random();

        int n1 = 100_000;
        int[] arrayN1 = random.ints(n1, -100_000, 100_000).toArray();


        int n2 = 1_000_000;
        int[] arrayN2 = random.ints(n2, -100_000, 100_000).toArray();

        long start1 = System.nanoTime();
        HeapSort.sort(arrayN1);
        long end1 = System.nanoTime();
        double time1Ms = (end1 - start1) / 1_000_000.0;

        long start2 = System.nanoTime();
        HeapSort.sort(arrayN2);
        long end2 = System.nanoTime();
        double time2Ms = (end2 - start2) / 1_000_000.0;

        System.out.println(time2Ms / time1Ms);
    }
}
