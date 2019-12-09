package com.grizwold.aoc.intcode;

import static com.grizwold.aoc.intcode.ParameterMode.IMMEDIATE;
import static com.grizwold.aoc.intcode.ParameterMode.POSITION;

interface Opcode {
    boolean matching(int opcodeDef);

    void execute(VM vm);

    default ParameterMode[] paramModes(String opcode) {
        ParameterMode[] paramModes = new ParameterMode[3];
        paramModes[0] = opcode.matches("..1..") ? IMMEDIATE : POSITION;
        paramModes[1] = opcode.matches(".1...") ? IMMEDIATE : POSITION;
        paramModes[2] = opcode.matches("1....") ? IMMEDIATE : POSITION;
        return paramModes;
    }

    default int getArg1(VM vm, ParameterMode[] paramModes) {
        return getValue(vm.instructionPointer + 1, paramModes[0], vm);
    }

    default int getArg2(VM vm, ParameterMode[] paramModes) {
        return getValue(vm.instructionPointer + 2, paramModes[1], vm);
    }

    default int getValue(int index, ParameterMode paramMode, VM vm) {
        return paramMode == IMMEDIATE ?
                vm.memory[index] :
                vm.memory[vm.memory[index]];
    }
}
