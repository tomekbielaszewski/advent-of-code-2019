package com.grizwold.aoc.intcode;

import static com.grizwold.aoc.intcode.ParameterMode.IMMEDIATE;

class Opcode_08 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(long opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...08");
    }

    @Override
    public void execute(VM vm) {
        long arg1 = getArg1(vm, paramModes);
        long arg2 = getArg2(vm, paramModes);
        int resultPointer = getArg3AsResult(vm, paramModes);

        String description = String.format("if %s == %s then 1 (@ %s); operation saved %s", arg1, arg2, resultPointer, vm.memory[resultPointer]);
        System.out.println(printInstruction(vm, 4, paramModes, description));

        vm.memory[resultPointer] = (arg1 == arg2) ? 1 : 0;
        vm.instructionPointer += 4;
    }
}
