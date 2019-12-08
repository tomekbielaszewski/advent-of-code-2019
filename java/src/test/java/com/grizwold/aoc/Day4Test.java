package com.grizwold.aoc;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertThat;

public class Day4Test {
    private final Day4 day4 = new Day4();

    @Test
    public void examples() {
        example("111111", true);
        example("223450", false);
        example("123789", false);
    }

    @Test
    public void solution() {
        System.out.println("Solution for day 4 part 1 is: " +
                IntStream.range(357253, 892943)
                        .filter(i -> day4.isValid(String.valueOf(i)))
                        .count()
        );
    }

    private void example(String pass, boolean valid) {
        assertThat(day4.isValid(pass), Is.is(valid));
    }
}
