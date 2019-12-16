package com.grizwold.aoc.intcode;

import static com.grizwold.aoc.intcode.ParameterMode.IMMEDIATE;

class Opcode_01 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(long opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...01");
    }

    @Override
    public void execute(VM vm) {
        long arg1 = getArg1(vm, paramModes);
        long arg2 = getArg2(vm, paramModes);
        long result = arg1 + arg2;
        int resultPointer = (int) getValue(vm.instructionPointer + 3, IMMEDIATE, vm);

        String description = String.format("%s + %s = %s (@ %s)", arg1, arg2, result, resultPointer);
        System.out.println(printInstruction(vm, 4, paramModes, description));

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;
    }
}
