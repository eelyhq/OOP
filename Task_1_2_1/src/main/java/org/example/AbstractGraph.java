package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Abstract base class implementing common Graph operations.
 *
 * @param <V> the vertex type
 */
public abstract class AbstractGraph<V> implements Graph<V> {
    private final Function<String, V> defaultVertexParser;

    public AbstractGraph() {
        this(null);
    }

    public AbstractGraph(Function<String, V> defaultVertexParser) {
        this.defaultVertexParser = defaultVertexParser;
    }

    protected V parseVertex(String s) {
        if (defaultVertexParser != null) {
            return defaultVertexParser.apply(s);
        }
        @SuppressWarnings("unchecked")
        V result = (V) s;
        return result;
    }

    @Override
    public void readFromFile(Path path) throws IOException {
        readFromFile(path, this::parseVertex);
    }

    @Override
    public void readFromFile(Path path, Function<String, V> vertexParser) throws IOException {
        Objects.requireNonNull(path, "path must not be null");
        Objects.requireNonNull(vertexParser, "vertexParser must not be null");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length == 1) {
                    addVertex(vertexParser.apply(parts[0]));
                } else if (parts.length == 2) {
                    V from = vertexParser.apply(parts[0]);
                    V to = vertexParser.apply(parts[1]);

                    addVertex(from);
                    addVertex(to);

                    addEdge(from, to);
                } else {
                    throw new IllegalArgumentException("Incorrect data format: " + line);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph<?> otherGraph)) {
            return false;
        }

        List<V> thisVertices = this.getVertices();
        List<?> otherVertices = otherGraph.getVertices();

        if (thisVertices == null || otherVertices == null) {
            return thisVertices == otherVertices;
        }

        if (thisVertices.size() != otherVertices.size()) {
            return false;
        }

        Set<V> thisVertSet = new HashSet<>(thisVertices);
        Set<?> otherVertSet = new HashSet<>(otherVertices);

        if (!thisVertSet.equals(otherVertSet)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Graph<V> other = (Graph<V>) otherGraph;

        for (V vertex : thisVertSet) {
            List<V> thisAdj = this.getAdjacentVertices(vertex);
            List<V> otherAdj = other.getAdjacentVertices(vertex);

            if (thisAdj == null || otherAdj == null) {
                if (thisAdj != otherAdj) {
                    return false;
                }
                continue;
            }

            if (thisAdj.size() != otherAdj.size()) {
                return false;
            }

            Set<V> thisNeighbours = new HashSet<>(thisAdj);
            Set<V> otherNeighbours = new HashSet<>(otherAdj);

            if (!thisNeighbours.equals(otherNeighbours)) {
                return false;
            }
        }
        return true; 
    }

    @Override 
    public int hashCode() {
        int hash = 0;
        List<V> vertices = getVertices();
        if (vertices == null) {
            return 0;
        }

        for (V vertex : new HashSet<>(vertices)) {
            List<V> adjacent = getAdjacentVertices(vertex);
            Set<V> neighbours = adjacent != null ? new HashSet<>(adjacent) : Collections.emptySet();
            hash += Objects.hash(vertex, neighbours);
        }

        return hash;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph {\n");
        List<V> vertices = getVertices();
        if (vertices != null) {
            for (V vertex : vertices) {
                sb.append("    ")
                        .append(vertex)
                        .append(" -> ")
                        .append(getAdjacentVertices(vertex))
                        .append("\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
}
