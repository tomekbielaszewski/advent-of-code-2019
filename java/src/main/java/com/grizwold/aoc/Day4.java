package com.grizwold.aoc;

import org.apache.commons.lang3.StringUtils;

public class Day4 {
    public boolean isValid(String password) {
        if (StringUtils.isBlank(password)) return false;
        if (!StringUtils.isNumeric(password)) return false;
        if (password.length() != 6) return false;
        if (digitsDecreases(password)) return false;
        return hasDoubledAdjacentDigits(password);
    }

    private boolean hasDoubledAdjacentDigits(String password) {
        char[] chars = password.toCharArray();
        char last = chars[0];
        boolean repeats = false;
        for (int i = 1; i < chars.length; i++) {
            if (last == chars[i]) repeats = true;
            last = chars[i];
        }
        return repeats;
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
