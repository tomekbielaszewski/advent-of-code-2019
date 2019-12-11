package com.grizwold.aoc.intcode;

import java.util.concurrent.BlockingQueue;

import static com.grizwold.aoc.intcode.ParameterMode.IMMEDIATE;

class Opcode_03 implements Opcode {
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        return opcode.matches("...03");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int input = read(vm.in);
        int resultPointer = getValue(vm.instructionPointer + 1, IMMEDIATE, vm);

        vm.memory[resultPointer] = input;
        vm.instructionPointer += 2;

        System.out.printf("read to %s\n", resultPointer);
    }

    private Integer read(BlockingQueue<Integer> in) {
        try {
            return in.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
