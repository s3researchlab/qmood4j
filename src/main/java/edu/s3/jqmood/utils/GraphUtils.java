package edu.s3.jqmood.utils;

import java.util.HashSet;
import java.util.Set;

import com.google.common.graph.MutableGraph;

public class GraphUtils {

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private GraphUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static <E> void addNodeIfNotExist(MutableGraph<E> graph, E value) {

        if (!graph.nodes().contains(value)) {
            graph.addNode(value);
        }
    }

    public static <E> void addEdgeIfNotExist(MutableGraph<E> graph, E value1, E value2) {

        if (!graph.hasEdgeConnecting(value1, value2)) {
            graph.putEdge(value1, value2);
        }
    }

    public static <E> Set<E> getSuccessorsFrom(MutableGraph<E> graph, E value) {

        Set<E> nodes = new HashSet<>();

        for (E successor : graph.successors(value)) {
            nodes.addAll(getSuccessorsFrom(graph, successor));
            nodes.add(successor);
        }

        return nodes;
    }

    public static <E> Set<E> getPredecessorsFrom(MutableGraph<E> graph, E value) {

        Set<E> nodes = new HashSet<>();

        for (E predecessor : graph.predecessors(value)) {
            nodes.addAll(getPredecessorsFrom(graph, predecessor));
            nodes.add(predecessor);
        }

        return nodes;
    }
}
