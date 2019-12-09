package com.grizwold.aoc.intcode;

class Opcode_05 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...05");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int result = getArg1(vm, paramModes);
        int jump = getArg2(vm, paramModes);
        if (result != 0) {
            vm.instructionPointer = jump;
        } else {
            vm.instructionPointer += 3;
        }

        System.out.printf("jump to %s if %s != 0 | modes: %s\n", jump, result, paramModes[0]);
    }
}
