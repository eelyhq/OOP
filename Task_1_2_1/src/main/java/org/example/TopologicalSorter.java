package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface for topological sorting of a graph.
 *
 * @param <V> the vertex type
 */
@FunctionalInterface 
public interface TopologicalSorter<V> {
    List<V> sort(Graph<V> graph);

    /**
     * Verifies that the given order of vertices is a valid topological sort.
     *
     * @param graph the graph
     * @param order the sorted list of vertices
     * @param <V>   the vertex type
     * @return true if valid, false otherwise
     */
    static <V> boolean verifyTopologicalOrder(Graph<V> graph, List<V> order) {
        if (graph == null || order == null) {
            return false;
        }

        List<V> vertices = graph.getVertices();
        if (vertices == null || vertices.size() != order.size()) {
            return false;
        }

        Map<V, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            indexMap.put(order.get(i), i);
        }

        if (indexMap.size() != order.size()) {
            return false;
        }

        for (V v : vertices) {
            if (!indexMap.containsKey(v)) {
                return false;
            }
        }

        List<Edge<V>> edges = graph.getEdges();
        if (edges != null) {
            for (Edge<V> edge : edges) {
                Integer fromIndex = indexMap.get(edge.getFrom());
                Integer toIndex = indexMap.get(edge.getTo());

                if (fromIndex == null || toIndex == null || fromIndex >= toIndex) {
                    return false;
                }
            }
        }

        return true;
    }
}
