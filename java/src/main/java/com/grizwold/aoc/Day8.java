package com.grizwold.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day8 {

    public int checksum(String input, int x, int y) {
        char[] digits = input.toCharArray();
        int layersAmount = digits.length / (x * y);
        List<char[][]> layers = new ArrayList<>();

        for (int i = 0; i < layersAmount; i++) {
            char[][] layer = new char[x][y];
            for (int _x = 0; _x < x; _x++) {
                for (int _y = 0; _y < y; _y++) {
                    int offset = x * y * i;
                    int digitIndex = offset + (_y * x) + _x;
                    layer[_x][_y] = digits[digitIndex];
                }
            }
            layers.add(layer);
        }

        return layers.stream()
                .min(this::compareZeroDigits)
                .map(layer -> calcDigits(layer, '1') * calcDigits(layer, '2'))
                .orElse(0);
    }

    private int compareZeroDigits(char[][] ints, char[][] ints1) {
        return calcDigits(ints, '0') - calcDigits(ints1, '0');
    }

    private int calcDigits(char[][] layer, char digit) {
        int result = 0;
        for (int i = 0; i < layer.length; i++) {
            for (int j = 0; j < layer[i].length; j++) {
                result += layer[i][j] == digit ? 1 : 0;
            }
        }
        return result;
    }
}
