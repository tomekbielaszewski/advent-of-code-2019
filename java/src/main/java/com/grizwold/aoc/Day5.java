package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

import java.io.InputStream;
import java.io.PrintStream;

public class Day5 {
    public int[] run(String program, InputStream in, PrintStream out) {
        VM vm = new VM(in, out);
        return vm.execute(program);
    }
}

