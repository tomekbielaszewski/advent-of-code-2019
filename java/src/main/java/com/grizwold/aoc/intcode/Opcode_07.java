package com.grizwold.aoc.intcode;

import static com.grizwold.aoc.intcode.ParameterMode.IMMEDIATE;

class Opcode_07 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...07");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);
        vm.memory[resultPointer] = (arg1 < arg2) ? 1 : 0;
        vm.instructionPointer += 4;

        System.out.printf("if %s < %s then 1 (@ %s); operation saved %s | modes: %s\n", arg1, arg2, resultPointer, vm.memory[resultPointer], paramModes[0]);
    }
}
