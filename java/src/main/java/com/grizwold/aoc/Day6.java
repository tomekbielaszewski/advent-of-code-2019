package com.grizwold.aoc;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 {
    private String root = "COM";

    public int orbitsTotal(String input) {
        Map<String, Node> nodes = read(input);
        return nodes.values().stream()
                .mapToInt(this::calcOrbitals)
                .sum();
    }

    private int calcOrbitals(Node node) {
        Node parent = node.parent;
        int orbitals = 0;
        while(parent != null) {
            orbitals ++;
            parent = parent.parent;
        }
        return orbitals;
    }

    private Map<String, Node> read(String input) {
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
