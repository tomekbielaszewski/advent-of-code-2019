package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

public class Day7 {
    public int runSequence(String program, Integer[] sequence) {
        if (sequence == null || sequence.length != 5)
            throw new IllegalArgumentException("sequence has to be 5 digits long");

        int lastResult = 0;
        for (int i = 0; i < sequence.length; i++) {
            int input = sequence[i];
            lastResult = run(program, input, lastResult);
        }

        return lastResult;
    }

    private int run(String program, int input1, int input2) {
        VM vm = new VM(input1 + " " + input2);
        vm.execute(program);
        return Integer.parseInt(vm.getOutputString().replaceAll("\\s", ""));
    }
}
