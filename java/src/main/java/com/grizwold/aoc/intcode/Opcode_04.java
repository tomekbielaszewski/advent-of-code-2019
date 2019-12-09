package com.grizwold.aoc.intcode;

class Opcode_04 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...04");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int result = getArg1(vm, paramModes);
        vm.instructionPointer += 2;
        vm.out.println(result);

        System.out.printf(" print: \"%s\" | modes: %s\n", result, paramModes[0]);

    }
}
