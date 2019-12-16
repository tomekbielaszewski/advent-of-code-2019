package com.grizwold.aoc.intcode;

class Opcode_02 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(long opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...02");
    }

    @Override
    public void execute(VM vm) {
        long arg1 = getArg1(vm, paramModes);
        long arg2 = getArg2(vm, paramModes);
        long result = arg1 * arg2;
        int resultPointer = getArg3AsResult(vm, paramModes);

        String description = String.format("%s * %s = %s (@ %s)", arg1, arg2, result, resultPointer);
        System.out.println(printInstruction(vm, 4, paramModes, description));

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;
    }
}
