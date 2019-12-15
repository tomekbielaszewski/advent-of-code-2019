package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

import java.util.concurrent.BlockingQueue;

public class Day5 {
    public long[] run(String program, BlockingQueue<Long> in, BlockingQueue<Long> out) {
        VM vm = new VM(in, out);
        return vm.execute(program);
    }
}

