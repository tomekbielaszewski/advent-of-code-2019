package com.grizwold.aoc;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 {
    private String root = "COM";

    public int orbitsTotal(String input) {
        Map<String, Node> nodes = readAsTree(input);
        return nodes.values().stream()
                .mapToInt(this::calcOrbitals)
                .sum();
    }

    private final String START = "YOU";
    private final String END = "SAN";

    public int orbitalPathLength(String input) {
        Graph g = readAsGraph(input);
        g.dijkstra(START);
        return g.pathLength(END) - 2;
    }

    private int calcOrbitals(Node node) {
        Node parent = node.parent;
        int orbitals = 0;
        while (parent != null) {
            orbitals++;
            parent = parent.parent;
        }
        return orbitals;
    }

    private Map<String, Node> readAsTree(String input) {
        List<String[]> rawNodes = Arrays.stream(input.split("\n"))
                .map(row -> row.split("\\)"))
                .collect(Collectors.toList());

        HashMap<String, Node> tree = new HashMap<>();
        for (String[] rawNode : rawNodes) {
            String parent = rawNode[0];
            String child = rawNode[1];

            Node parentNode = Optional.ofNullable(tree.get(parent))
                    .orElse(new Node(parent));
            Node childNode = Optional.ofNullable(tree.get(child))
                    .orElse(new Node(child));

            parentNode.addChild(childNode);

            tree.put(parent, parentNode);
            tree.put(child, childNode);
        }

        return tree;
    }

    private Graph readAsGraph(String input) {
        List<Graph.Edge> edges = Arrays.stream(input.split("\n"))
                .map(row -> row.split("\\)"))
                .map(row -> new Graph.Edge(row[0], row[1], 1))
                .collect(Collectors.toList());

        return new Graph(edges);
    }

    private class Node {
        Node parent;
        String name;
        List<Node> children = new ArrayList<>();

        public Node(String name) {
            this.name = name;
        }

        Node addChild(Node child) {
            children.add(child);
            child.parent = this;
            return this;
        }
    }

}

// https://raw.githubusercontent.com/TheAlgorithms/Java/master/Others/Dijkstra.java
class Graph {
    private final Map<String, Vertex> graph;

    public static class Edge {
        public final String v1, v2;
        public final int dist;

        public Edge(String v1, String v2, int dist) {
            this.v1 = v1;
            this.v2 = v2;
            this.dist = dist;
        }
    }

    public static class Vertex implements Comparable<Vertex> {
        public final String name;
        public int dist = Integer.MAX_VALUE;
        public Vertex previous = null;
        public final Map<Vertex, Integer> neighbours = new HashMap<>();

        public Vertex(String name) {
            this.name = name;
        }

        private int calcPathLength() {
            if (this == this.previous) {
                return 0;
            } else if (this.previous == null) {
                return Integer.MAX_VALUE;
            } else {
                return 1 + this.previous.calcPathLength();
            }
        }

        public int compareTo(Vertex other) {
            if (dist == other.dist)
                return name.compareTo(other.name);

            return Integer.compare(dist, other.dist);
        }

        @Override
        public String toString() {
            return "(" + name + ", " + dist + ")";
        }
    }

    public Graph(List<Edge> edges) {
        graph = new HashMap<>(edges.size());

        // one pass to find all vertices
        for (Edge e : edges) {
            if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
            if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
        }

        // another pass to set neighbouring vertices
        for (Edge e : edges) {
            graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
            graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
        }
    }

    public void dijkstra(String startName) {
        if (!graph.containsKey(startName)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
            return;
        }
        final Vertex source = graph.get(startName);
        NavigableSet<Vertex> q = new TreeSet<>();

        // set-up vertices
        for (Vertex v : graph.values()) {
            v.previous = v == source ? source : null;
            v.dist = v == source ? 0 : Integer.MAX_VALUE;
            q.add(v);
        }

        dijkstra(q);
    }

    private void dijkstra(final NavigableSet<Vertex> q) {
        Vertex u, v;
        while (!q.isEmpty()) {
            // vertex with shortest distance (first iteration will return source)
            u = q.pollFirst();
            if (u.dist == Integer.MAX_VALUE)
                break; // we can ignore u (and any other remaining vertices) since they are unreachable

            // look at distances to each neighbour
            for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
                v = a.getKey(); // the neighbour in this iteration

                final int alternateDist = u.dist + a.getValue();
                if (alternateDist < v.dist) { // shorter path to neighbour found
                    q.remove(v);
                    v.dist = alternateDist;
                    v.previous = u;
                    q.add(v);
                }
            }
        }
    }

    public int pathLength(String endName) {
        if (!graph.containsKey(endName)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
            return 0;
        }

        return graph.get(endName).calcPathLength();
    }

    public void printAllPaths() {
        for (Vertex v : graph.values()) {
            v.calcPathLength();
            System.out.println();
        }
    }
}
