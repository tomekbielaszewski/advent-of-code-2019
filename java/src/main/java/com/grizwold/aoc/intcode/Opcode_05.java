package com.grizwold.aoc.intcode;

class Opcode_05 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(long opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...05");
    }

    @Override
    public void execute(VM vm) {
        long result = getArg1(vm, paramModes);
        long jump = getArg2(vm, paramModes);

        String description = String.format("jump to %s if %s != 0", jump, result);
        System.out.println(printInstruction(vm, 3, paramModes, description));

        if (result != 0) {
            vm.instructionPointer = (int) jump;
        } else {
            vm.instructionPointer += 3;
        }
    }
}
