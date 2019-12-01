package com.grizwold.aoc;

import java.util.List;

public class Day1 {
    public int solution1(List<String> input) {
        return input.stream()
                .map(Integer::parseInt)
                .map(this::calcFuel)
                .reduce(0, Integer::sum);
    }

    public int solution2(List<String> input) {
        return input.stream()
                .map(Integer::parseInt)
                .map(this::calcExtraFuel)
                .reduce(0, Integer::sum);
    }

    private int calcFuel(Integer mass) {
        return mass / 3 - 2;
    }

    private int calcExtraFuel(Integer mass) {
        int fuel = calcFuel(mass);
        if (fuel > 0) return fuel + calcExtraFuel(fuel);
        else return 0;
    }
}
