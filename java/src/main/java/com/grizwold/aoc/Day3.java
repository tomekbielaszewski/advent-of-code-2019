package com.grizwold.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    private static final Point STARTING_POINT = new Point(0, 0);

    public long manhattanDistanceToClosestIntersection(String input) {
        String[] routes = input.split("\n");
        List<Point> route1Points = routeToPoints(routes[0]);
        List<Point> route2Points = routeToPoints(routes[1]);
        List<Point> intersections = getIntersections(route1Points, route2Points);
        return getManhattanDistanceToClosestIntersection(intersections);
    }

    public long stepsDistanceToClosestIntersection(String input) {
        String[] routes = input.split("\n");
        List<Point> route1Points = routeToPoints(routes[0]);
        List<Point> route2Points = routeToPoints(routes[1]);
        List<Point> intersections = getIntersections(route1Points, route2Points);
        return getStepsDistanceToClosestIntersection(route1Points, route2Points, intersections);
    }

    private long getStepsDistanceToClosestIntersection(List<Point> route1, List<Point> route2, List<Point> intersections) {
        return intersections.parallelStream()
                .mapToLong(i -> route1.indexOf(i) + route2.indexOf(i))
                .min()
                .getAsLong();
    }

    private long getManhattanDistanceToClosestIntersection(List<Point> intersections) {
        return intersections.parallelStream()
                .mapToLong(p -> Math.abs(p.x) + Math.abs(p.y))
                .min()
                .getAsLong();
    }

    private List<Point> getIntersections(List<Point> route1, List<Point> route2) {
        return route1.parallelStream()
                .filter(p -> !p.equals(STARTING_POINT))
                .filter(route2::contains)
                .collect(Collectors.toList());
    }

    private List<Point> routeToPoints(String route) {
        String[] lines = route.split(",");
        Point from = STARTING_POINT;
        List<Point> points = new ArrayList<>();
        for (String line : lines) {
            List<Point> linePoints = lineToPoints(from, line);
            points.addAll(linePoints);
            from = linePoints.get(linePoints.size() - 1);
        }
        return points.stream().distinct().collect(Collectors.toList());
    }

    private List<Point> lineToPoints(Point from, String line) {
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

    private List<Point> right(Point from, int distance) {
        List<Point> points = new ArrayList<>();
        for (int i = from.x; i <= distance + from.x; i++) {
            points.add(new Point(i, from.y));
        }
        return points;
    }

    private List<Point> left(Point from, int distance) {
        List<Point> points = new ArrayList<>();
        for (int i = from.x; i >= from.x - distance; i--) {
            points.add(new Point(i, from.y));
        }
        return points;
    }

    private List<Point> down(Point from, int distance) {
        List<Point> points = new ArrayList<>();
        for (int i = from.y; i >= from.y - distance; i--) {
            points.add(new Point(from.x, i));
        }
        return points;
    }

    private List<Point> up(Point from, int distance) {
        List<Point> points = new ArrayList<>();
        for (int i = from.y; i <= from.y + distance; i++) {
            points.add(new Point(from.x, i));
        }
        return points;
    }
}
























