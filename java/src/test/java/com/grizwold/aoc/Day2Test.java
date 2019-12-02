package com.grizwold.aoc;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class Day2Test {
    private final Day2 day2 = new Day2();

    @Test
    public void examples1() {
        example("1,9,10,3,2,3,11,0,99,30,40,50", "3500,9,10,70,2,3,11,0,99,30,40,50");
        example("1,0,0,0,99", "2,0,0,0,99");
        example("2,3,0,3,99", "2,3,0,6,99");
        example("2,4,4,5,99,0", "2,4,4,5,99,9801");
        example("1,1,1,4,99,5,6,0,99", "30,1,1,4,2,5,6,0,99");
    }

    @Test
    public void solution1() {
        int[] input = day2.toArray("1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,9,19,23,1,6,23,27,1,10,27,31,1,5,31,35,2,6,35,39,1,5,39,43,1,5,43,47,2,47,6,51,1,51,5,55,1,13,55,59,2,9,59,63,1,5,63,67,2,67,9,71,1,5,71,75,2,10,75,79,1,6,79,83,1,13,83,87,1,10,87,91,1,91,5,95,2,95,10,99,2,9,99,103,1,103,6,107,1,107,10,111,2,111,10,115,1,115,6,119,2,119,9,123,1,123,6,127,2,127,10,131,1,131,6,135,2,6,135,139,1,139,5,143,1,9,143,147,1,13,147,151,1,2,151,155,1,10,155,0,99,2,14,0,0");

        try {
            day2.solution1(input);
        } catch (ProgramTerminated e) {
            System.out.println(input[0]);
        }
    }

    @Test
    public void solution2() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int[] input = day2.toArray("1," + i + "," + j + ",3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,9,19,23,1,6,23,27,1,10,27,31,1,5,31,35,2,6,35,39,1,5,39,43,1,5,43,47,2,47,6,51,1,51,5,55,1,13,55,59,2,9,59,63,1,5,63,67,2,67,9,71,1,5,71,75,2,10,75,79,1,6,79,83,1,13,83,87,1,10,87,91,1,91,5,95,2,95,10,99,2,9,99,103,1,103,6,107,1,107,10,111,2,111,10,115,1,115,6,119,2,119,9,123,1,123,6,127,2,127,10,131,1,131,6,135,2,6,135,139,1,139,5,143,1,9,143,147,1,13,147,151,1,2,151,155,1,10,155,0,99,2,14,0,0");
                try {
                    day2.solution1(input);
                } catch (ProgramTerminated e) {
                    if (input[0] == 19690720) {
                        System.out.println(100 * i + j);
                    }
                }
            }
        }
    }

    private void example(String inputStr, String resultStr) {
        int[] input = day2.toArray(inputStr);
        int[] expected = day2.toArray(resultStr);

        try {
            day2.solution1(input);
        } catch (ProgramTerminated e) {
            assertThat(input, equalTo(expected));
            return;
        }
        fail();
    }
}
