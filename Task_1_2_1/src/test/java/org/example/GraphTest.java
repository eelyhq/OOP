package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTest {

    private void checkGraphOperations(Graph<String> graph) {
        assertTrue(graph.addVertex("A"));
        assertTrue(graph.addVertex("B"));
        assertTrue(graph.addVertex("C"));
        assertFalse(graph.addVertex("A"));
        assertFalse(graph.addVertex(null));
        assertTrue(graph.containsVertex("A"));
        assertFalse(graph.containsVertex("D"));

        assertTrue(graph.addEdge("A", "B"));
        assertTrue(graph.addEdge("A", "C"));
        assertFalse(graph.addEdge("A", "B"));
        assertFalse(graph.addEdge("A", "D"));
        assertTrue(graph.containsEdge("A", "B"));
        assertFalse(graph.containsEdge("B", "A"));

        assertEquals(3, graph.getVertices().size());
        List<String> adj = graph.getAdjacentVertices("A");
        assertEquals(2, adj.size());
        assertTrue(adj.contains("B") && adj.contains("C"));
        assertThrows(IllegalArgumentException.class, () -> graph.getAdjacentVertices("Unknown"));

        assertTrue(graph.removeEdge("A", "B"));
        assertFalse(graph.containsEdge("A", "B"));
        assertFalse(graph.removeEdge("A", "B"));

        assertTrue(graph.removeVertex("C"));
        assertFalse(graph.containsVertex("C"));
        assertFalse(graph.containsEdge("A", "C"));
        assertFalse(graph.removeVertex("C"));
    }

    @Test
    void testAdjacencyListGraph() {
        checkGraphOperations(new AdjacencyListGraph<>());
    }

    @Test
    void testAdjacencyMatrixGraph() {
        checkGraphOperations(new AdjacencyMatrixGraph<>());
    }

    @Test
    void testIncidenceMatrixGraph() {
        checkGraphOperations(new IncidenceMatrixGraph<>());
    }

    @Test
    void testGraphEqualityAndHashCode() {
        Graph<String> list = new AdjacencyListGraph<>();
        Graph<String> matrix = new AdjacencyMatrixGraph<>();
        Graph<String> inc = new IncidenceMatrixGraph<>();

        List<Graph<String>> allGraphs = List.of(list, matrix, inc);
        for (Graph<String> g : allGraphs) {
            g.addVertex("A");
            g.addVertex("B");
            g.addEdge("A", "B");
        }

        assertEquals(list, matrix);
        assertEquals(list, inc);
        assertEquals(list.hashCode(), matrix.hashCode());
        assertFalse(list.equals(null));
        assertFalse(list.equals("string"));

        Graph<String> other = new AdjacencyListGraph<>();
        other.addVertex("A");
        assertFalse(list.equals(other));

        assertTrue(list.toString().contains("A -> [B]"));
    }

    @Test
    void testReadFromFile() throws IOException {
        Path path = Path.of("input.txt");

        Graph<String> graph = new AdjacencyListGraph<>();
        graph.readFromFile(path);

        assertTrue(graph.containsVertex("A"));
        assertTrue(graph.containsVertex("B"));
        assertTrue(graph.containsVertex("F"));
        assertTrue(graph.containsEdge("A", "B"));
        assertTrue(graph.containsEdge("D", "E"));
    }

    @Test
    void testTopologicalSort() {
        Graph<String> graph = new AdjacencyListGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        TopologicalSorter<String> sorter = new KanhTopologicalSort<>();
        List<String> sorted = sorter.sort(graph);
        assertEquals(List.of("A", "B", "C"), sorted);
    }

    @Test
    void testTopologicalSortCycle() {
        Graph<String> graph = new AdjacencyMatrixGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.addEdge("B", "A");

        TopologicalSorter<String> sorter = new KanhTopologicalSort<>();
        assertThrows(IllegalStateException.class, () -> sorter.sort(graph));
    }

    @Test
    void testMain() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
