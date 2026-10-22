package org.example;

import java.util.List;

@FunctionalInterface 
public interface TopologicalSorter<V> {
    List<V> sort(Graph<V> graph);
}