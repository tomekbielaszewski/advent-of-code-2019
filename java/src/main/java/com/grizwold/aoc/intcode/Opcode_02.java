package com.grizwold.aoc.intcode;

import java.util.Arrays;

import static com.grizwold.aoc.intcode.ParameterMode.IMMEDIATE;

class Opcode_02 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...02");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int result = arg1 * arg2;
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;

        System.out.printf("%s * %s = %s (@ %s) | parameter modes: %s\n", arg1, arg2, result, resultPointer, Arrays.toString(paramModes));
    }
}
