package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class KanhTopologicalSort<V> implements TopologicalSorter<V> {
    @Override 
    public List<V> sort(Graph<V> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        Map<V, Integer> inDegree = new HashMap<>();

        for (V v : graph.getVertices()) {
            inDegree.put(v, 0);
        }

        for (V u : graph.getVertices()) {
            for (V v : graph.getAdjacentVertices(u)) {
                inDegree.put(v, inDegree.get(v) + 1);
            }
        }

        Queue<V> zeroInDegreeQueue = new ArrayDeque<>();
        for (Map.Entry<V, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                zeroInDegreeQueue.add(entry.getKey());
            }
        }

        List<V> result = new ArrayList<>();

        while (!zeroInDegreeQueue.isEmpty()) {
            V current = zeroInDegreeQueue.poll();
            result.add(current);

            for (V neighbour : graph.getAdjacentVertices(current)) {
                int updatedDegree = inDegree.get(neighbour) - 1;
                inDegree.put(neighbour, updatedDegree);

                if (updatedDegree == 0) {
                    zeroInDegreeQueue.add(neighbour);
                }
            }
        }

        if (result.size() != graph.getVertices().size()) {
            throw new IllegalStateException("Graph has a cycle, sorting is impossible");
        }

        return result;
    }
}
