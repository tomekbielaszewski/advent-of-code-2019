package com.grizwold.aoc.intcode;

import java.util.Arrays;

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
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        long arg1 = getArg1(vm, paramModes);
        long arg2 = getArg2(vm, paramModes);
        long result = arg1 + arg2;
        int resultPointer = (int) getValue(vm.instructionPointer + 3, IMMEDIATE, vm);

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;

        System.out.printf("%s + %s = %s (@ %s) | parameter modes: %s\n", arg1, arg2, result, resultPointer, Arrays.toString(paramModes));
    }
}
