package org.example;

import java.util.List;

/**
 * Interface for topological sorting of a graph.
 *
 * @param <V> the vertex type
 */
@FunctionalInterface 
public interface TopologicalSorter<V> {
    List<V> sort(Graph<V> graph);
}
