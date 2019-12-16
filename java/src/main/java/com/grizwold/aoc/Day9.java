package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

public class Day9 {

    public Long[] run(String program, Long inputs) {
        VM vm = new VM(inputs);
        vm.execute(program, 10000);
        return vm.getOutputs();
    }

    public Long[] run(String program) {
        return this.run(program, 0L);
    }
}
