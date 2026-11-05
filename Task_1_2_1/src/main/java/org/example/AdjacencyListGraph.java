package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Graph implementation using adjacency lists.
 *
 * @param <V> the vertex type
 */
public class AdjacencyListGraph<V> extends AbstractGraph<V> {
    private final Map<V, Set<V>> adjacencyMap = new HashMap<>();

    public AdjacencyListGraph() {
        super();
    }

    public AdjacencyListGraph(Function<String, V> defaultVertexParser) {
        super(defaultVertexParser);
    }

    @Override 
    public boolean containsVertex(V vertex) {
        return vertex != null && adjacencyMap.containsKey(vertex);
    }

    @Override 
    public List<V> getVertices() {
        return new ArrayList<>(adjacencyMap.keySet());
    }

    @Override
    public boolean addVertex(V vertex) {
        if (vertex == null || containsVertex(vertex)) {
            return false;
        }
        adjacencyMap.put(vertex, new HashSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(V vertex) {
        if (!containsVertex(vertex)) {
            return false;
        }

        adjacencyMap.remove(vertex);

        for (Set<V> neighbours : adjacencyMap.values()) {
            neighbours.remove(vertex);
        }
        return true;
    }

    @Override
    public List<V> getAdjacentVertices(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.emptyList();
        } 

        return new ArrayList<>(adjacencyMap.get(vertex));
    }

    @Override 
    public boolean addEdge(V from, V to) { 
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }
        return adjacencyMap.get(from).add(to);
    }

    @Override
    public boolean removeEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }   
        return adjacencyMap.get(from).remove(to);
    }

    @Override 
    public boolean containsEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }  
        return adjacencyMap.get(from).contains(to);
    }


}
