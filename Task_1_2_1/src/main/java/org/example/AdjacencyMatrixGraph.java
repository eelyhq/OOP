package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AdjacencyMatrixGraph<V> extends AbstractGraph<V> {
    private final Map<V, Integer> vertexToIndex = new HashMap<>();
    private final List<V> indexToVertex = new ArrayList<>();
    private final List<List<Boolean>> matrix = new ArrayList<>();

    public AdjacencyMatrixGraph() {
        super();
    }

    public AdjacencyMatrixGraph(Function<String, V> defaultVertexParser) {
        super(defaultVertexParser);
    }

    @Override
    public boolean containsVertex(V vertex) {
        return vertex != null && vertexToIndex.containsKey(vertex);
    }

    @Override 
    public List<V> getVertices() {
        return new ArrayList<>(indexToVertex);
    } 

    @Override
    public boolean addVertex(V vertex) {
        if (vertex == null || containsVertex(vertex)) {
            return false;
        }   

        int newIndex = indexToVertex.size();
        vertexToIndex.put(vertex, newIndex);
        indexToVertex.add(vertex);

        for (List<Boolean> row : matrix) {
            row.add(false);
        }

        List<Boolean> newRow = new ArrayList<>(newIndex + 1);
        for (int i = 0; i <= newIndex; i++) {
            newRow.add(false);
        }
        matrix.add(newRow);

        return true;
    }

    @Override 
    public boolean removeVertex(V vertex) {
        if (!containsVertex(vertex)) {
            return false;
        }

        int index = vertexToIndex.remove(vertex);
        indexToVertex.remove(index);
        
        matrix.remove(index);

        for (List<Boolean> row : matrix) {
            row.remove(index);
        }

        for (int i = index; i < indexToVertex.size(); i++) {
            vertexToIndex.put(indexToVertex.get(i), i);
        }

        return true;
    }

    @Override 
    public List<V> getAdjacentVertices(V vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex doesn't exist in graph: " + vertex);
        }

        int row = vertexToIndex.get(vertex);
        List<V> neighbours = new ArrayList<>();
        List<Boolean> rowValues = matrix.get(row);

        for (int col = 0; col < rowValues.size(); col++) {
            if (Boolean.TRUE.equals(rowValues.get(col))) {
                neighbours.add(indexToVertex.get(col));
            }
        }

        return neighbours;
    }

    @Override 
    public boolean addEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }

        int row = vertexToIndex.get(from);
        int col = vertexToIndex.get(to);

        if (Boolean.TRUE.equals(matrix.get(row).get(col))) {
            return false;
        }

        matrix.get(row).set(col, true);
        return true;
    }

    @Override 
    public boolean removeEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }

        int row = vertexToIndex.get(from);
        int col = vertexToIndex.get(to);

        if (Boolean.FALSE.equals(matrix.get(row).get(col))) {
            return false;
        }

        matrix.get(row).set(col, false);
        return true;
    }

    @Override 
    public boolean containsEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }
        
        int row = vertexToIndex.get(from);
        int col = vertexToIndex.get(to);

        return matrix.get(row).get(col);
    }
}
