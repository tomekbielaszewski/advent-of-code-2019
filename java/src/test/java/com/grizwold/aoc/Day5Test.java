package com.grizwold.aoc;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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

        example2("3,9,8,9,10,9,4,9,99,-1,8", "8", "1"); //input should be equal to 8 (position mode)
        example2("3,9,8,9,10,9,4,9,99,-1,8", "7", "0"); //input should be equal to 8 (position mode)

        example2("3,9,7,9,10,9,4,9,99,-1,8", "7", "1"); //input should be less than 8 (position mode)
        example2("3,9,7,9,10,9,4,9,99,-1,8", "8", "0"); //input should be less than 8 (position mode)

        example2("3,3,1108,-1,8,3,4,3,99", "8", "1"); //input should be equal to 8 (immediate mode)
        example2("3,3,1108,-1,8,3,4,3,99", "9", "0"); //input should be equal to 8 (immediate mode)

        example2("3,3,1107,-1,8,3,4,3,99", "7", "1"); //input should be less than 8 (immediate mode)
        example2("3,3,1107,-1,8,3,4,3,99", "8", "0"); //input should be less than 8 (immediate mode)

        example2("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", "7", "1"); //input should be non equal to 0 (immediate mode)
        example2("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", "0", "0"); //input should be non equal to 0 (immediate mode)

        example2("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "78", "1"); //input should be non equal to 0 (position mode)
        example2("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "0", "0"); //input should be non equal to 0 (position mode)
    }

    private void example(String inputStr, String resultStr) {
        Day5 day5 = new Day5();
        int[] expected = day5.toArray(resultStr);
        int[] memoryAfter = day5.run(inputStr, System.in, System.out);

        assertThat(memoryAfter, equalTo(expected));
    }

    private void example2(String program, String input, String expectedOutput) {
        InputStream vmInput = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream vmOut = new PrintStream(outputStream);

        Day5 day5 = new Day5();
        day5.run(program, vmInput, vmOut);

        String output = new String(outputStream.toByteArray());
        assertThat(output, is(expectedOutput));
    }
}
