package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class IncidenceMatrixGraph<V> extends AbstractGraph<V> {
    private final Map<V, Integer> vertexToIndex = new HashMap<>();
    private final List<V> indexToVertex = new ArrayList<>();

    private final List<Edge<V>> edges = new ArrayList<>();  
    private final List<List<Integer>> matrix = new ArrayList<>();

    public IncidenceMatrixGraph() {
        super();
    }

    public IncidenceMatrixGraph(Function<String, V> defaultVertexParser) {
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

        List<Integer> newRow = new ArrayList<>(edges.size());
        for (int i = 0; i < edges.size(); i++) {
            newRow.add(0);
        }
        matrix.add(newRow);

        return true;
    }
    
    private void removeColumn(int col) {
        edges.remove(col);
        for (List<Integer> row : matrix) {
            row.remove(col);
        }
    }
    
    @Override
    public boolean removeVertex(V vertex) {
        if (!containsVertex(vertex)) {
            return false;
        }

        for (int col = edges.size() - 1; col >= 0; col--) {
            Edge<V> edge = edges.get(col);
            if (edge.getFrom().equals(vertex) || edge.getTo().equals(vertex)) {
                removeColumn(col);
            }
        }

        int removeIndex = vertexToIndex.remove(vertex);
        indexToVertex.remove(removeIndex); 
        matrix.remove(removeIndex);

        for (int i = removeIndex; i < indexToVertex.size(); i++) {
            vertexToIndex.put(indexToVertex.get(i), i);
        }

        return true;
    }

    @Override
    public List<V> getAdjacentVertices(V vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex doesn't exist in graph: " + vertex);
        }

        int vertexRow = vertexToIndex.get(vertex);
        List<V> neighbours = new ArrayList<>();
        
        for (int col = 0; col < edges.size(); col++) {
            if (matrix.get(vertexRow).get(col) == -1) {
                for (int row = 0; row < indexToVertex.size(); row++) {
                    if (matrix.get(row).get(col) == 1) {
                        neighbours.add(indexToVertex.get(row));
                        break;
                    }
                }
            } else if (matrix.get(vertexRow).get(col) == 2) {
                neighbours.add(vertex);
            } 
        }

        return neighbours;
    }

    @Override
    public boolean addEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }

        Edge<V> newEdge = new Edge<>(from, to);
        if (edges.contains(newEdge)) {
            return false;
        }
        
        edges.add(newEdge);

        int indexFrom = vertexToIndex.get(from);
        int indexTo = vertexToIndex.get(to);

        if (indexFrom != indexTo) {
            for (int row = 0; row < indexToVertex.size(); row++) {
                if (row == indexFrom) {
                    matrix.get(row).add(-1);
                } else if (row == indexTo) {
                    matrix.get(row).add(1);
                } else {
                    matrix.get(row).add(0);
                }
            }
        } else {
            for (int row = 0; row < indexToVertex.size(); row++) {
                if (row == indexFrom) {
                    matrix.get(row).add(2);
                } else {
                    matrix.get(row).add(0);
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }

        Edge<V> edgeToRemove = new Edge<>(from, to);
        
        int edgeIndex = edges.indexOf(edgeToRemove);
        if (edgeIndex == -1) {
            return false;
        }
        
        removeColumn(edgeIndex);
        return true;
    }

    @Override
    public boolean containsEdge(V from, V to) {
        if (!containsVertex(from) || !containsVertex(to)) {
            return false;
        }

        return edges.contains(new Edge<>(from, to));
    }
}
