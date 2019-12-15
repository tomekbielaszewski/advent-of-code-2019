package com.grizwold.aoc.intcode;

import static com.grizwold.aoc.intcode.ParameterMode.*;

interface Opcode {
    boolean matching(long opcodeDef);

    void execute(VM vm);

    default ParameterMode[] paramModes(String opcode) {
        ParameterMode[] paramModes = new ParameterMode[3];
        paramModes[0] = opcode.matches("..1..") ? IMMEDIATE :
                opcode.matches("..2..") ? RELATIVE : POSITION;
        paramModes[1] = opcode.matches(".1...") ? IMMEDIATE :
                opcode.matches(".2...") ? RELATIVE : POSITION;
        paramModes[2] = opcode.matches("1....") ? IMMEDIATE :
                opcode.matches("2....") ? RELATIVE : POSITION;
        return paramModes;
    }

    default long getArg1(VM vm, ParameterMode[] paramModes) {
        return getValue(vm.instructionPointer + 1, paramModes[0], vm);
    }

    default long getArg2(VM vm, ParameterMode[] paramModes) {
        return getValue(vm.instructionPointer + 2, paramModes[1], vm);
    }

    default long getValue(int index, ParameterMode paramMode, VM vm) {
        return paramMode == IMMEDIATE ?
                vm.memory[index] :
                paramMode == POSITION ?
                        vm.memory[(int) vm.memory[index]] :
                        vm.memory[(int) vm.memory[vm.relativeBase + index]];
    }
}
