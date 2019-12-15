package com.grizwold.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

    public int checksum(String input, int x, int y) {
        char[] digits = input.toCharArray();
        int layersAmount = digits.length / (x * y);
        List<char[][]> layers = buildLayers(digits, layersAmount, x, y);
        return layers.stream()
                .min(this::compareZeroDigits)
                .map(layer -> calcDigits(layer, '1') * calcDigits(layer, '2'))
                .orElse(0);
    }

    public char[][] mergeLayers(String input, int x, int y) {
        char[] digits = input.toCharArray();
        int layersAmount = digits.length / (x * y);
        List<char[][]> layers = buildLayers(digits, layersAmount, x, y);
        char[][] result = new char[x][y];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                final int _x = i;
                final int _y = j;
                List<Character> pixel = layers.stream()
                        .map(l -> l[_x][_y])
                        .collect(Collectors.toList());
                result[i][j] = mergePixel(pixel);
            }
        }
        return result;
    }

    public void print(char[][] image) {
        System.out.println();
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                char c = image[i][j];
                switch (c) {
                    case '0':
                        System.out.print(" ");
                        break;
                    case '1':
                        System.out.print("#");
                        break;
                    case '2':
                        System.out.print(" ");
                        break;
                }
            }
            System.out.println();
        }
    }

    private char mergePixel(List<Character> pixel) {
        for (Character subPixel : pixel) {
            if (subPixel == '1' || subPixel == '0')
                return subPixel;
        }
        return '2';
    }

    private List<char[][]> buildLayers(char[] digits, int layersAmount, int x, int y) {
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
        return layers;
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
