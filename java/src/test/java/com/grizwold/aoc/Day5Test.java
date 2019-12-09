package com.grizwold.aoc;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class Day5Test {
    @Test
    public void examples() {
        //verification from day 2
        example("1,9,10,3,2,3,11,0,99,30,40,50", "3500,9,10,70,2,3,11,0,99,30,40,50");
        example("1,0,0,0,99", "2,0,0,0,99");
        example("2,3,0,3,99", "2,3,0,6,99");
        example("2,4,4,5,99,0", "2,4,4,5,99,9801");
        example("1,1,1,4,99,5,6,0,99", "30,1,1,4,2,5,6,0,99");

        //test cases from day 5
        example("1002,4,3,4,33", "1002,4,3,4,99");
    }

    private void example(String inputStr, String resultStr) {
        Day5 day5 = new Day5();
        int[] expected = day5.toArray(resultStr);
        int[] memoryAfter = day5.run(inputStr);

        assertThat(memoryAfter, equalTo(expected));
    }
}
