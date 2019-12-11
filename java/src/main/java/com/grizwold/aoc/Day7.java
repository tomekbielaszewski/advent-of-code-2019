package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public int runSequenceInFeedbackLoop(String program, Integer[] sequence) {
        return 0;
    }

    private Integer run(String program, int input1, int input2) {
        VM vm = new VM(input1, input2);
        vm.execute(program);
        return vm.getOutput().poll();
    }
}
