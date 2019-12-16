package com.grizwold.aoc.intcode;

class Opcode_09 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(long opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...09");
    }

    @Override
    public void execute(VM vm) {
        long arg1 = getArg1(vm, paramModes);

        String description = String.format("relative base adjusted by %s to %s", arg1, vm.relativeBase);
        System.out.println(printInstruction(vm, 2, paramModes, description));

        vm.relativeBase += arg1;
        vm.instructionPointer += 2;
    }
}
