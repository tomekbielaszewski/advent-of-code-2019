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
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        long arg1 = getArg1(vm, paramModes);
        vm.relativeBase += arg1;
        vm.instructionPointer += 2;

        System.out.printf("relative base adjusteb by %s to %s | modes: %s\n", arg1, vm.relativeBase, paramModes[0]);
    }
}
