package com.grizwold.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    private static final int[] STARTING_POINT = new int[]{0, 0};

    public long manhattanDistanceToClosestIntersection(String input) {
        String[] routes = input.split("\n");
        List<int[]> route1Points = routeToPoints(routes[0]);
        List<int[]> route2Points = routeToPoints(routes[1]);
        List<int[]> intersections = getIntersections(route1Points, route2Points);
        return getDistanceToClosestIntersection(intersections);
    }

    private long getDistanceToClosestIntersection(List<int[]> intersections) {
        return intersections.stream()
                .mapToLong(point -> Math.abs(point[0]) + Math.abs(point[1]))
                .min()
                .getAsLong();
    }

    public List<int[]> getIntersections(List<int[]> route1, List<int[]> route2) {
        return route1.stream()
                .filter(p -> !Arrays.equals(p, STARTING_POINT))
                .filter(p1 -> route2.stream()
                        .anyMatch(p2 -> p1[0] == p2[0] && p1[1] == p2[1]))
                .collect(Collectors.toList());
    }

    private List<int[]> routeToPoints(String route) {
        String[] lines = route.split(",");
        int[] from = STARTING_POINT;
        List<int[]> points = new ArrayList<>();
        for (String line : lines) {
            List<int[]> linePoints = lineToPoints(from, line);
            points.addAll(linePoints);
            from = linePoints.get(linePoints.size() - 1);
        }
        return points;
    }

    private List<int[]> lineToPoints(int[] from, String line) {
        char direction = line.charAt(0);
        int distance = Integer.parseInt(line.substring(1));
        switch (direction) {
            case 'R':
                return right(from, distance);
            case 'L':
                return left(from, distance);
            case 'D':
                return down(from, distance);
            case 'U':
                return up(from, distance);
            default:
                throw new IllegalArgumentException("" + direction);
        }
    }

    private List<int[]> right(int[] from, int distance) {
        List<int[]> points = new ArrayList<>();
        for (int i = from[0]; i <= distance + from[0]; i++) {
            points.add(new int[]{i, from[1]});
        }
        return points;
    }

    private List<int[]> left(int[] from, int distance) {
        List<int[]> points = new ArrayList<>();
        for (int i = from[0]; i >= from[0] - distance; i--) {
            points.add(new int[]{i, from[1]});
        }
        return points;
    }

    private List<int[]> down(int[] from, int distance) {
        List<int[]> points = new ArrayList<>();
        for (int i = from[1]; i >= from[1] - distance; i--) {
            points.add(new int[]{from[0], i});
        }
        return points;
    }

    private List<int[]> up(int[] from, int distance) {
        List<int[]> points = new ArrayList<>();
        for (int i = from[1]; i <= from[1] + distance; i++) {
            points.add(new int[]{from[0], i});
        }
        return points;
    }
}
