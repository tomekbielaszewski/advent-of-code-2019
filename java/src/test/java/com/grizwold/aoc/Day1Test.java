package com.grizwold.aoc;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class Day1Test {
    private final Day1 d1 = new Day1();

    @Test
    public void example1_1() {
        assertThat(d1.solution1(singletonList("12")), is(2));
    }

    @Test
    public void example1_2() {
        assertThat(d1.solution1(singletonList("14")), is(2));
    }

    @Test
    public void example1_3() {
        assertThat(d1.solution1(singletonList("1969")), is(654));
    }

    @Test
    public void example1_4() {
        assertThat(d1.solution1(singletonList("100756")), is(33583));
    }

    @Test
    public void example2_1() {
        assertThat(d1.solution2(singletonList("14")), is(2));
    }

    @Test
    public void example2_2() {
        assertThat(d1.solution2(singletonList("1969")), is(966));
    }

    @Test
    public void example2_3() {
        assertThat(d1.solution2(singletonList("100756")), is(50346));
    }

    private List<String> input = Arrays.asList(
            "54296",
            "106942",
            "137389",
            "116551",
            "129293",
            "60967",
            "142448",
            "101720",
            "64463",
            "142264",
            "68673",
            "144661",
            "110426",
            "59099",
            "63711",
            "120365",
            "125233",
            "126793",
            "61990",
            "122059",
            "86768",
            "134293",
            "114985",
            "61280",
            "75325",
            "103102",
            "116332",
            "112075",
            "114895",
            "98816",
            "59389",
            "124402",
            "74995",
            "135512",
            "115619",
            "59680",
            "61421",
            "141160",
            "148880",
            "70010",
            "119379",
            "92155",
            "126698",
            "138653",
            "149004",
            "142730",
            "68658",
            "73811",
            "87064",
            "62684",
            "93335",
            "140475",
            "143377",
            "98445",
            "117960",
            "80237",
            "132483",
            "108319",
            "104154",
            "99383",
            "104685",
            "114888",
            "73376",
            "58590",
            "132759",
            "114399",
            "77796",
            "119228",
            "136282",
            "84789",
            "66511",
            "51939",
            "142313",
            "117305",
            "139543",
            "92054",
            "64606",
            "139795",
            "109051",
            "97040",
            "91850",
            "107391",
            "60200",
            "75812",
            "74898",
            "64884",
            "115210",
            "85055",
            "92256",
            "67470",
            "90286",
            "129142",
            "109235",
            "117194",
            "104028",
            "127482",
            "68502",
            "92440",
            "50369",
            "84878"
    );

    @Test
    public void solution1() {
        System.out.println("d1.s1: " + d1.solution1(input));
    }

    @Test
    public void solution2() {
        System.out.println("d1.s2: " + d1.solution2(input));
    }
}
