package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main application class.
 */
public class Main {
    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Path filePath;
        if (args.length > 0) {
            filePath = Paths.get(args[0]);
        } else {
            filePath = Paths.get("input.txt");
            if (!Files.exists(filePath)) {
                filePath = Paths.get("src/main/resources/input.txt");
            }
        }

        System.out.println("Reading graph from: " + filePath.toAbsolutePath());

        try {
            // 1. Create all three representations
            Graph<String> listGraph = new AdjacencyListGraph<>();
            Graph<String> matrixGraph = new AdjacencyMatrixGraph<>();
            Graph<String> incGraph = new IncidenceMatrixGraph<>();

            // 2. Read from file
            listGraph.readFromFile(filePath);
            matrixGraph.readFromFile(filePath);
            incGraph.readFromFile(filePath);

            System.out.println("\n--- Adjacency List Graph ---");
            System.out.print(listGraph);

            // 3. Verify equality between representations
            boolean listEqualsMatrix = listGraph.equals(matrixGraph);
            boolean listEqualsInc = listGraph.equals(incGraph);
            boolean matrixEqualsInc = matrixGraph.equals(incGraph);

            System.out.println("listGraph.equals(matrixGraph): " + listEqualsMatrix);
            System.out.println("listGraph.equals(incGraph):    " + listEqualsInc);
            System.out.println("matrixGraph.equals(incGraph):  " + matrixEqualsInc);

            if (!listEqualsMatrix || !listEqualsInc || !matrixEqualsInc) {
                System.err.println("Error: Graph representations are not equivalent!");
                return;
            }

            // 4. Perform topological sort
            TopologicalSorter<String> sorter = new KanhTopologicalSort<>();
            List<String> sortedVertices = sorter.sort(listGraph);

            System.out.println("\nTopological sort result: " + sortedVertices);

            // 5. Verify topological sort correctness
            boolean isValid = TopologicalSorter.verifyTopologicalOrder(listGraph, sortedVertices);
            System.out.println("Is topological sort valid? " + isValid);

            System.out.println("\nAll checks passed successfully!");
        } catch (IOException e) {
            System.err.println("Failed to read graph file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
        }
    }
}
