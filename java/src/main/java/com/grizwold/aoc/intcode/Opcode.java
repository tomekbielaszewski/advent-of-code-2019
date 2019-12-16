package com.grizwold.aoc.intcode;

import java.util.Arrays;

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

    default int getArg1AsResult(VM vm, ParameterMode[] paramModes) {
        long pointer = getValue(vm.instructionPointer + 1, IMMEDIATE, vm);
        if(paramModes[0]==RELATIVE)
            pointer += vm.relativeBase;
        return (int) pointer;
    }

    default long getArg2(VM vm, ParameterMode[] paramModes) {
        return getValue(vm.instructionPointer + 2, paramModes[1], vm);
    }

    default int getArg3AsResult(VM vm, ParameterMode[] paramModes) {
        long pointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);
        if(paramModes[2]==RELATIVE)
            pointer += vm.relativeBase;
        return (int) pointer;
    }

    default long getValue(int index, ParameterMode paramMode, VM vm) {
        return paramMode == IMMEDIATE ?
                vm.memory[index] :
                paramMode == POSITION ?
                        vm.memory[(int) vm.memory[index]] :
                        vm.memory[(int) (vm.relativeBase + vm.memory[index])];
    }

    default String printInstruction(VM vm, int instructionLength, ParameterMode[] paramModes, String description) {
        long[] instr = new long[instructionLength];
        System.arraycopy(vm.memory, vm.instructionPointer, instr, 0, instructionLength);
        return String.format("@%-3s IP:%-6s REL:%-5s %-35s %-35s %s",
                vm.instructionPointer,
                Utils.toString(vm.memory[vm.instructionPointer]),
                vm.relativeBase,
                Arrays.toString(instr),
                Arrays.toString(paramModes),
                description);
    }
}
