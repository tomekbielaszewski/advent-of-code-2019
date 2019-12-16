package com.grizwold.aoc.intcode;

import java.util.concurrent.BlockingQueue;

class Opcode_04 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(long opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...04");
    }

    @Override
    public void execute(VM vm) {
        long result = getArg1(vm, paramModes);

        String description = String.format("print: \"%s\"", result);
        System.out.println(printInstruction(vm, 2, paramModes, description));

        vm.instructionPointer += 2;
        write(vm.out, result);
    }

    private void write(BlockingQueue<Long> out, long result) {
        try {
            out.put(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
