package com.grizwold.aoc.intcode;

class Opcode_99 implements Opcode {
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        return opcode.matches("...99");
    }

    @Override
    public void execute(VM vm) {
        vm.isRunning = false;

        System.out.printf("@%s {%s} VM finished execution\n", vm.instructionPointer, opcode);
    }
}
