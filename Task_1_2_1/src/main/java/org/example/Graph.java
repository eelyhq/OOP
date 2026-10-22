package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public interface Graph<V> {
    boolean addVertex(V vertex);

    boolean removeVertex(V vertex);

    boolean containsVertex(V vertex);

    List<V> getVertices();

    List<V> getAdjacentVertices(V vertex);

    boolean addEdge(V from, V to);

    boolean removeEdge(V from, V to);

    boolean containsEdge(V from, V to);

    void readFromFile(Path path) throws IOException;

    void readFromFile(Path path, Function<String, V> vertexParser) throws IOException;
}
