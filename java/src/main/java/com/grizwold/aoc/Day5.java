package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

import java.util.concurrent.BlockingQueue;

public class Day5 {
    public int[] run(String program, BlockingQueue<Integer> in, BlockingQueue<Integer> out) {
        VM vm = new VM(in, out);
        return vm.execute(program);
    }
}

