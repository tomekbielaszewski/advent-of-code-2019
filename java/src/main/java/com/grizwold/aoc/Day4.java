package com.grizwold.aoc;

import org.apache.commons.lang3.StringUtils;

import static java.util.stream.Collectors.groupingBy;

public class Day4 {
    public boolean isValid(String password) {
        if (StringUtils.isBlank(password)) return false;
        if (!StringUtils.isNumeric(password)) return false;
        if (password.length() != 6) return false;
        if (digitsDecreases(password)) return false;
        return hasDoubledAdjacentDigits(password);
    }

    private boolean hasDoubledAdjacentDigits(String password) {
        return password.chars()
                .boxed()
                .collect(groupingBy(i -> i))
                .values()
                .stream()
                .anyMatch(l -> l.size() == 2);

    }

    private boolean digitsDecreases(String password) {
        char[] chars = password.toCharArray();
        char last = chars[0];
        for (int i = 1; i < chars.length; i++) {
            if (last > chars[i]) return true;
            last = chars[i];
        }
        return false;
    }
}
