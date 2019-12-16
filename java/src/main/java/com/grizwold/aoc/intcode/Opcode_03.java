package com.grizwold.aoc.intcode;

import java.util.concurrent.BlockingQueue;

class Opcode_03 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(long opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...03");
    }

    @Override
    public void execute(VM vm) {
        long input = read(vm.in);
        int resultPointer = getArg1AsResult(vm, paramModes);

        String description = String.format("read %s into %s", input, resultPointer);
        System.out.println(printInstruction(vm, 2, paramModes, description));

        vm.memory[resultPointer] = input;
        vm.instructionPointer += 2;
    }

    private Long read(BlockingQueue<Long> in) {
        try {
            return in.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
